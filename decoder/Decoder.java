/*
Ronnye Alvarado - 2019
LZW Algorithm Decoder
This decoder is meant to be used along with the encoder I created. The encoder uses dictionary coding which is especially useful in applications where the output of source consists of recurring patterns. 
I tested the encoder/decoder using 5 files of at least 1MB where similar patterns recur.

The decoder by default reads the encoded file: encodedFileNew. If you changed the file name that the encoded sequence is written to, you will have to change this as well in line 21 in the decoder. 
The decoded sequence will be written to file decodedFileNew. If you would like to change the file name or write it to a different file, you can modify the FileWriter in line 43.
If the decoded file doesn't display properly in Eclipse, or a different IDE, I suggest saving the file to your desktop as a .txt file and open using Notepad or any equivalent. 
I'm not sure if this is an issue on my end or Eclipse, however, using the recommended strategy, you will see that it does in fact contain the decoded sequence. 
If you compared the decoded sequence to the original source file, you will see that they are exactly the same.
*/

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Decoder {
	public static void main(String[] args) throws Exception {
		
		//creates a file that the scanner can use to read the base dictionary created by the encoder
		File scannerFile = new File("src/encodedFileNew");
		

		//scanner will read the encoded file and put the base dictionary into the decoderDictionary map
		Scanner input = new Scanner(new FileReader(scannerFile));
		Map<Integer, String> decoderDictionary = new HashMap<Integer, String>();
		
		
		//encodedSequence created by encoder is put into this list
		ArrayList <Integer> encodedSequence = new ArrayList<Integer>();
		
		
		//will contain the decoded sequence
		ArrayList <String> decodedSequence = new ArrayList<String>();
		
		
		/*this variable is to keep the dictionary in order starting from 0
		if I used the index of the for loop, it would not work as intended */
		int dictionaryPosition = -1;
		

		//This will write to the decoded file with original,decoded sequence
		FileWriter decodedWriter = new FileWriter ("src/decodedFileNew");
		
		//while counter keeps track of the index of the encodedSequence
		int whileCounter = 0;
		
		
		//input skips a line since the first line is the encoded sequence itself
		input.nextLine();
		while (input.hasNextLine()) {
			//this is to prevent the program from crashing if there are any blank spaces after the dictionary
			if (input.hasNext() != true)
				break;
				
			//splits the dictionary and puts it into its respective key and value
			String temp [] = input.nextLine().split(" ");
			decoderDictionary.put(Integer.parseInt(temp[0]), temp[1]);
			dictionaryPosition = dictionaryPosition + 1;
		}
		input.close();
		
		
		//this reads the encodedSequence created by the encoder
		Scanner encodedInput = new Scanner(new FileReader(scannerFile));
		
		
		//messy code but basically just takes the encoded sequence that's in the first line and turns it into an ArrayList<Integer>
		String eString = encodedInput.nextLine();
		String [] eStringsplit = eString.split(" ");
		int[] numbers = new int[eStringsplit.length];
		
		for(int i = 0;i < eStringsplit.length; i++) {
			numbers[i] = Integer.parseInt(eStringsplit[i]);
		}
		for (int i = 0; i < numbers.length; i++) {
			encodedSequence.add(numbers[i]);
		}
		encodedInput.close();

		
		System.out.println("Decoding the encoded file...");
		
		//while not at end of encoded sequence
		while (whileCounter < encodedSequence.size()) {
			
			//take current element and add it with a question mark to the dictionary
			int current = encodedSequence.get(whileCounter);
			String question = "?";

			//if the dictionary decoder contains the key(index) associated with that encoder, put value + "?" into the next dictionary slot
			if (decoderDictionary.containsKey(current)) {
				decodedSequence.add(decoderDictionary.get(current));
				
				//if at end of sequence print out decoded sequence and add it to the decoded file
				if (whileCounter + 1 >= encodedSequence.size()) {
					 decodedWriter.write(Arrays.toString(decodedSequence.toArray()).replace(",", "").replace("[", "").replace("]", "").replace(" ", ""));
					break;
				}

				//the unknown string is what is put into the dictionary with "?" at the end
				String unknown = decoderDictionary.get(current) + question;
				dictionaryPosition = dictionaryPosition + 1;
				decoderDictionary.put(dictionaryPosition, unknown);
				
				//the next string is what follows the current index and removes the "?" with the next element
				String next = decoderDictionary.get(encodedSequence.get(whileCounter + 1));
				String updated = decoderDictionary.get(dictionaryPosition).replace(question, next.substring(0,1));
				decoderDictionary.put(dictionaryPosition, updated);
			}
			whileCounter++;	
		}
		decodedWriter.close();
		System.out.println("The encoded file has been decoded and stored in file decodedFileNew.");
	}
}
