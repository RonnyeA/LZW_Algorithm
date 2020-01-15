# LZW_Algorithm
LZW Algorithm - Decoder README

This decoder is meant to be used along with the encoder I created. 
The encoder uses dictionary coding which is especially useful in applications where the output of source consists of recurring patterns. 
I tested the encoder/decoder using 5 files of at least 1MB where similar patterns recur.
The decoder by default reads the encoded file: encodedFileNew. 
If you changed the file name that the encoded sequence is written to, you will have to change this as well in line 21 in the decoder. 
The decoded sequence will be written to file decodedFileNew. 
If you would like to change the file name or write it to a different file, you can modify the FileWriter in line 43.
If the decoded file doesn't display properly in Eclipse, or a different IDE, I suggest saving the file to your desktop as a .txt file and open using Notepad or any equivalent. 
I'm not sure if this is an issue on my end or Eclipse, however, using the recommended strategy, you will see that it does in fact contain the decoded sequence. 
If you compared the decoded sequence to the original source file, you will see that they are exactly the same.
