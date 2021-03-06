package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import controller.MenuViewController;
import dataStructures.AvlTree;


public class DataGenerator extends Thread {
	private MenuViewController controller;
    private AvlTree<Person> peopleByName;
    private AvlTree<Person> peopleByLastName;
    private AvlTree<Person> peopleByFullname;
    private AvlTree<Person> peopleById;
	
    private int totalPeople;    
    private int[][] ageData;
    private float[] ageMarks;
    private String[][] countriesData; 
    private int[][][] heightData;
    private final int ageRange=5;
    private final int heightRange=17;  
    private final int countries = 55; 
    private int[][] distribution;
    private int[] countryPopulation;
    
    public DataGenerator(int totalPeople, MenuViewController controller, Database database) {
    	this.controller = controller;
    	this.peopleByFullname = database.getPeopleByFullname();
    	this.peopleByName = database.getPeopleByName();
    	this.peopleByLastName = database.getPeopleByLastname();
    	this.peopleById = database.getPeopleById();
    	this.totalPeople = totalPeople;
    }
    
    @Override
    public void run() {
    	try {
			setup();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	public void setup() throws Exception {
    	BufferedReader inCountry = new BufferedReader(new FileReader(new File("./data/countries.csv")));
        ageData = new int[ageRange][2];
        ageMarks = new float[ageRange];
        heightData = new int[2][heightRange][2];
        countriesData = new String[countries][2];
    
        loadGeneratorData();
        
        countryPopulation = new int[countries];
        distribution = new int[countries][ageRange];

        // Población por país
        int generated = 0;
        for (int i=0; i<countries; i++) {
            String[] parameters = inCountry.readLine().split(";");
            countriesData[i][0] = parameters[0];
            countriesData[i][1] = parameters[1];

            countryPopulation[i] = Math.round(Float.parseFloat(parameters[2])*totalPeople);
            generated += countryPopulation[i];
        
            if (generated>totalPeople) { // Ajuste sobran datos
                countryPopulation[i] -= generated-totalPeople;
                break;
            }
        }

        if (generated<totalPeople) { // Ajuste faltan datos                     
            for (int i=0; i<totalPeople-generated; i++) {
                countryPopulation[i]++;
            }
        }

        // Población por edad
        
        for (int i=0; i<countryPopulation.length; i++) {
            int generated2 = 0;
            for (int j=0; j<ageRange; j++) {
                distribution[i][j] = Math.round(countryPopulation[i] * (ageMarks[j]/100));
                generated2 += distribution[i][j];
        
                if (generated2>countryPopulation[i]) { // Ajuste sobran datos
                    distribution[i][j] -= generated2-countryPopulation[i];
                    break;
                }
            }       
    
            if (generated2<countryPopulation[i]) { // Ajuste faltan datos
                for (int j=0; j<countryPopulation[i]-generated2; j++) distribution[i][j]++;
            }
        }

        generateData();

    }
	
	public void generateData() throws FileNotFoundException, Exception {
        LocalTime starting = LocalTime.now();
        
        for (int i=0; i<countries; i++) {
            int counter = 0;
            for (int j=0; j<ageRange; j++) {
                for (int k=0; k<distribution[i][j]; k++){
                    int gender = randomNumberBetween(0, 1);
                    String namePath = "./data/boyNames.csv";
                    if (gender == 1) namePath = "./data/girlNames.csv";
                    int age = randomNumberBetween(ageData[j][0], ageData[j][1]);
                    int tempAge = age;
                    if(age>16)tempAge = 16;
                    Person person = new Person(
                        countriesData[i][1] + counter,
                        getRandomLine(namePath),
                        getRandomLine("./data/lastnames.csv"),
                        age,
                        gender,
                        randomNumberBetween(heightData[gender][tempAge][0], heightData[gender][tempAge][1]),
                        countriesData[i][0]
                    );
                    
                    TreeAdder add1 = new TreeAdder(person, peopleByName); add1.start();
                    TreeAdder add2 = new TreeAdder(person, peopleByLastName); add2.start();
                    TreeAdder add3 = new TreeAdder(person, peopleByFullname); add3.start();
                    TreeAdder add4 = new TreeAdder(person, peopleById); add4.start();

                    add1.join();
                    add2.join();
                    add3.join();
                    add4.join();
                    controller.step();
                    
                    counter++;
                }
            }
        }
        
        int seconds = (int) starting.until(LocalTime.now(), ChronoUnit.SECONDS);
        controller.endOfGeneration(seconds);
    }
	
	public int randomNumberBetween(int min, int max) {
        return (int) (Math.random() * (max + 1 - min)) + min;
    }

    public String getRandomLine(String filePathWithFileName) throws Exception {
        String randomLine = null;

        while (randomLine == null){
            File file = new File(filePathWithFileName); 
            final RandomAccessFile f = new RandomAccessFile(file, "r");
            final long randomLocation = (long) (Math.random() * f.length());
            f.seek(randomLocation);
            f.readLine();
            randomLine = f.readLine();
            f.close();
        }

        return randomLine;
    }
    
    public void loadGeneratorData() throws IOException {
        BufferedReader inAge = new BufferedReader(new FileReader(new File("./data/ages.csv")));
        BufferedReader inMaleHeight = new BufferedReader(new FileReader(new File("./data/maleHeights.csv")));
        BufferedReader inFemaleHeight = new BufferedReader(new FileReader(new File("./data/femaleHeights.csv")));
        
        String[] line = null;
        String[] line2 = null;

        for (int i=0; i<ageRange; i++) {
            line = inAge.readLine().split(";");
            ageData[i][0] = Integer.parseInt(line[0]);
            ageData[i][1] = Integer.parseInt(line[1]);
            ageMarks[i] = Float.parseFloat(line[2]);
        } 
       
        for (int i=0; i<heightRange; i++) {
            line = inMaleHeight.readLine().split(";");
            heightData[0][i][0] = Integer.parseInt(line[0]);
            heightData[0][i][1] = Integer.parseInt(line[1]);

            line2 = inFemaleHeight.readLine().split(";");
            heightData[1][i][0] = Integer.parseInt(line2[0]);
            heightData[1][i][1] = Integer.parseInt(line2[1]);
            
        }
    }
}
