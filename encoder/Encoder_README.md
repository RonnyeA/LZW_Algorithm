# LZW_Algorithm
LZW Algorithm - Encoder README

The encoder uses dictionary coding which is especially useful in applications where the output of source consists of recurring patterns. I tested the encoder/decoder using 5 files of at least 1MB where similar patterns recur.

If you want to test your own file, you can easily add a new File and modify the FileReader on line 45 to use your specified file. After that, run the file and it will produce a file called encodedFileNew. You might have to modify/create the FileWriter destination on line 29 for it to work on your system.

If the encoded file doesn't display properly in Eclipse, or a different IDE, I suggest saving the file to your desktop as a .txt file and open using Notepad or any equivalent. Eclipse properly shows smaller encoded files but doesn't display files that have long encoded sequences. I'm not sure if this is an issue on my end or Eclipse, however, using the recommended strategy, you will see that it does in fact contain the encoded sequence.
