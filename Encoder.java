/*
Ronnye Alvarado - 2019
LZW Algorithm Encoder
The encoder uses dictionary coding which is especially useful in applications where the output of source consists of recurring patterns. I tested the encoder/decoder using 5 files of at least 1MB where similar patterns recur.

If you want to test your own file, you can easily add a new File and modify the FileReader on line 45 to use your specified file. After that, run the file and it will produce a file called encodedFileNew. 
You might have to modify/create the FileWriter destination on line 29 for it to work on your system.
If the encoded file doesn't display properly in Eclipse, or a different IDE, I suggest saving the file to your desktop as a .txt file and open using Notepad or any equivalent. 
Eclipse properly shows smaller encoded files but doesn't display files that have long encoded sequences. 
I'm not sure if this is an issue on my end or Eclipse, however, using the recommended strategy, you will see that it does in fact contain the encoded sequence.
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Encoder {
	public static void main(String[] args) throws Exception {
		
		//create new file
		//these files are the files that contain the data we want to encode
		File aTest = new File("src/aTest");
		File alphabetTest = new File("src/alphabetTest");
		File alphabetbackwardsTest = new File("src/alphabetbackwardsTest");
		File numbersTest = new File("src/numbersTest");
		File evenOddNumbersTest = new File("src/evenOddNumbersTest");

		//This will write to the encoded file with a base dictionary and encoded sequence
		FileWriter encodedWriter = new FileWriter ("src/encodedFileNew");
		
		/*created a map to use as a dictionary
		I picked this since I figured it would be the easiest way to implement
		The key is the index associated with the string (value) in the dictionary
		My code needs to be able to get the key associated with the value, since that is not possible with a map, I created an inverse that will work with map*/
		Map<Integer, String> dictionary = new HashMap<Integer, String>();
		Map<String, Integer> inverse = new HashMap<String, Integer>();
		
		
		//map that holds the base dictionary without the updates
		//I will use this and put it in the encoded file to send to the decoder
		Map<Integer, String> temp = new HashMap<Integer, String>();
		
		
		//created a filereader and bufferedreader to read the file char by char
		FileReader fr = new FileReader(alphabetbackwardsTest);
		BufferedReader br = new BufferedReader(fr);
		
		
		/*I created this ArrayList of Strings as a place holder to hold all characters
		ArrayList is also dynamic which is why I picked it*/
		ArrayList<String> stringArray = new ArrayList<String>();
		

		//this ArrayList will contain the encoded sequence
		ArrayList integerArray = new ArrayList();
	
		
		//while counter will track the position in the file
		int whileCounter = 0;	
		
		
		//this loop reads the file char by char
		//adds all the characters to the stringArray
		int characterIndex = 0;
		while ((characterIndex = br.read()) != -1) {
			char character = (char) characterIndex;
			stringArray.add(Character.toString(character));
		}
		
	
		/*this variable is to keep the dictionary in order starting from 0
		if I used the index of the for loop, it would not work as intended */
		int dictionaryPosition = -1;
		
		
		/*for loop will go through all the characters in arrayString
		the loop will add any non-duplicate characters to the Map*/
		for (int i = 0; i < stringArray.size(); i++) {
			if (!dictionary.containsValue(stringArray.get(i))) {
				dictionary.put(1 + dictionaryPosition, stringArray.get(i));
				inverse.put(stringArray.get(i), 1 + dictionaryPosition);
				dictionaryPosition = dictionaryPosition + 1;	
			}
		}
		
		
		//puts the base dictionary into the temp map that ONLY holds the base dictionary
		//this is easier since the dictionary will be updated as it encodes
		dictionary.forEach((key, value) -> {
			temp.put(key, dictionary.get(key));
		});
	
		
		//this is the first character in the sequence
		String initial = stringArray.get(whileCounter);
		
		System.out.println("Encoding the source file...");
		
		/*while you are not at the end of the stringArray
		if the dictionary already contains that sequence, try again but include the next symbol until the dictionary is missing that sequence
		else add that sequence to the dictionary */
		while (whileCounter < stringArray.size()) {
			
			//this makes sure you don't get an IndexOutOfBoundsException
			if (whileCounter + 1 == stringArray.size()) {
				integerArray.add(inverse.get(initial));
				break;
			}
			
			//checks the next character in the sequence
			String next = stringArray.get(whileCounter + 1);
			
			//if the dictionary already contains the current sequence, it will add another character to it
			if (dictionary.containsValue(initial + next)) {
				initial = initial + next;
			}
			
			/*this loop is to add the encoded numbers (map keys) to integerArray
			 integerArray will contain the encoded sequence*/ 
			else {
				for (int i = 0; i < dictionary.size(); i++) {
					if (dictionary.get(i).equals(initial));
						integerArray.add(inverse.get(initial));
						break;
				}
				
				/*adds the current sequence to the dictionary and the inverse and keeps the number ordering intact
				  points initial to the next character*/
				dictionary.put(dictionaryPosition + 1, initial + next);
				inverse.put(initial + next, dictionaryPosition + 1);
				dictionaryPosition = dictionaryPosition + 1;
				initial = stringArray.get(whileCounter + 1);
			}
			whileCounter++;
		}
		
		String formattedString = integerArray.toString().replace(",", "").replace("[", "").replace("]", "");//.replace(" ", "");
		//writes the encoded sequences to the file
		for (int i = 0; i != 1; i++) {
			encodedWriter.write(formattedString + System.lineSeparator());
		}
	
		//writes the base dictionary to the encoded file for the decoder to use
		temp.forEach((key, value) -> {
			try {
				encodedWriter.write(key + " " + value +"\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		encodedWriter.close();
		System.out.println("The encoded sequence is complete and stored in file encodedFileNew.");
	}
}