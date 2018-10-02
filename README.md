# LostConsonants (CS5001 Practical 1)

## Instructions
Quick section in how to use this project.

### To Compile
javac src/*.java

### To run studres stacscheck
stacscheck /cs/studres/CS5001/Practicals/p1-lostconsonants/Tests

### To run custom stacscheck
stacscheck stacscheck/

### To use the LostConsonant program
java src/LostConsonant words.txt <YOUR-STRING-HERE>

### To use the LostVowels program
java src/LostVowels words.txt <YOUR-STRING-HERE>

### To use the AddCharacter program
java src/AddCharacter words.txt <YOUR-STRING-HERE> <0, 1 OR BLANK>

0 == Entire Alphabet
1 == Vowels Only
BLANK == Consonant (default)

## LostConsonant Design Methodology and Process

### Description
A program to take a given dictionary and string, remove each consonant in turn and validate the mutated string against the words found in the given the dictionary. Prints and coutns all validated mutated
strings.

### Implementation
When designing the LostConsonant Program, I built it to run from a singular method loseConsonant(), however at over 100 lines long before development was completed I decided to refactor my code to be split
into reusable segments of code which would also reduce the bloater code smell (whilst avoiding dispensers) (for more information about Code Smells visit https://sourcemaking.com/refactoring/smells). 

After refactoring through devolving my code into methods, I performed further refactoring to generalise the code (aware of the LostVowels extension) and whilst making the code more resilient to user misuse.

Two issues did cause me some trouble. The first was ignoring capitalisation and punctuation whilst evaluating the dictionary (which was resolved after inspiration from a stackoverflow post (LostConsonant:180
for full reference). The second was the ordering of the remove ArrayList, I was concerned about the wrong elements in the results ArrayList being removed. So after realising if that I order the remove
ArrayList in descending order, then there will be no chance of the wrong element being removed. The quicksort method used was inspired by my previous Data Structures and Algorithm module.

### Testing
Once I was receiving acceptable outputs from the program, I ran stacscheck frequently, fixing errors until stacscheck brought nothing else. At that moment I evalulated the code for any code smells or general
Software Engineering mistakes before moving onto LostVowels.

## LostVowels Design Methodology and Process

### Description
A program to take a given dictionary and string, remove each vowel in turn and validate the mutated string against the words found in the given dictionary. Prints and counts all validated mutated strings.

### Implementation
This extension was very simple to implement thanks to the devolved refactoring that I had previously implemented in the LostConsonant program. I was unable to reuse the majority of my previous code to achieve 
this effect. 

The only challenge was how I accessed the class variables, as methods in the LostConsonants class would try to access the variable local to them (LocalConsonants.results rather than LocalVowels.results).
This was overcome by reading and writing to the class variables at the beginning and ending of each step of the program. 

### Testing
Wrote a custom stacscheck to automatically test the LostVowels program. Tests all bag arguments, failure cases and success cases. Instructions on how to run the custom stacs check can be found in the
instructions section of this README. Code Smells was manually evaluated and attended to as per necessary.

## AddCharacter Design Methodology and Process

### Description
A program to take a given dictionary, string and integer, add a character (character set decided by the integer - 0 for alphabet, 1 for vowels and blank (or any other number) for consonants (default)) to the
string and validate the entire string against the dictionary. Prints and counts the validated mutated strings. 

### Implementation
The AddCharacter extension seemed like a daunting task, I have to admit. However, once I wrote down the logic on paper it was very simple to implement. As with the LostVowels extension I was able to reuse
many of the methods from LostConsonants. However, I did have to define class custom argsCheck(), in addition to the code to add a character and validate rather than remove a character.

I decided to make this as robust and generalised as possible. I would have liked to combine LostVowels and LostConsonants with a user input declaring which character set to remove. So I did just that with the
AddCharacter extension. The third argument passed from the command line decides the character set.

### Testing
Wrote a custom stacscheck to automatically test the AddCharacter program. Tests all bag arguments, failure cases and success cases. Instructions on how to run the custom stacs check can be found in the
instructions section of this README. Code Smells was manually evaluated and attended to as per necessary.

