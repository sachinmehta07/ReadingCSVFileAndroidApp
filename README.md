# ReadingCSVFileAndroidApp
Simple app for reading a CSV file 


While making this simple app for reading a CSV file, we learned the following:

Accessing assets folder: We learned how to access the assets folder in an Android app to store static files. We used the getAssets().open() method to retrieve an InputStream for reading the contents of a file located in the assets folder.

Reading file content: We used the BufferedReader class to read the content of the CSV file. It allows us to read the file line by line using the readLine() method.

Parsing CSV: To parse the CSV data, we used the Apache Commons CSV library. We created a CSVParser object and provided the BufferedReader as the input. The library handles parsing the CSV data into records and fields, making it easier to work with structured CSV data.

Displaying data: We used a StringBuilder to collect the CSV data while iterating over the records and fields. Once the parsing was complete, we set the collected data as the text of a TextView to display it in the app's UI.

The main method we used was getAssets().open() to access the CSV file in the assets folder, BufferedReader to read the file content, and CSVParser to parse the CSV data. Additionally, we used standard Java I/O operations and the Apache Commons CSV library for CSV parsing.
