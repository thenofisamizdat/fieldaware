# fieldaware

* I've added a Main class so you can test from the command line
 *  If running from the command line, out/production/fieldaware is the root folder
 *  Run command is java com.fieldaware.Main log.txt
 
 *  NB - Normally I would not hard code the path as seen in **Main.java** with variable 
**String hardPathFallback**
    but I thought if you want to run from IntelliJ then just put the path to your log file here
 
 *  Main runs the log processor on the log file then allows us to query the processed file through the CLI
 
 * Tests in folder test/
 
 * Any questions, just let me know :) neil.byrne@gmail.com