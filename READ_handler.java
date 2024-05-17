public class READ_handler
{
	
 private static String content ;//Variable to store the contents of he file.
 
 private int index;//Variable to store the finger(index) position.
 /**
  * Constructor for initialization of the variables in the class
  * @param Documnet: The String that holds the whole awk document.
  */
 public READ_handler(String Document) 
 {
	 index=0;
	
	
	     content =  Document; //Storing file contents in a string.
 }
 /**
  * 
  * @param i: The number of character from the index to which the user wants to peek.
  * @return The character that is i characters ahead from the index.
  */
char Peek(int i)
{
	char ch = 0;//A variable to store the output.
	if((index+(i))>=0 && (index+(i))<content.length())//check if i change the index out of bound
	{
	ch=content.charAt(index+i); 
	}
	else
	{
		
		System.out.println("INDEX OUT OF BOUnD 2");
		System.exit(0);
	}
	return ch ;//return the character 
}
/**
 * 
 * @param i : The number of character from the index till which the user wants to peek
 * @return  The string from the index till i characters.
 */
String PeekString(int i)
{
	String nextichars;//To store the string from the current index till i.
	if((index+i)>0 && (index+i)<content.length())
	 nextichars=content.substring(index,(index+i));
	else 
	 nextichars=content.substring(index,content.length());//returns the whole string or the string from index till end if the index is out of bound
	return nextichars;
}
/**
 * 
 * @return The character with the current index and adds the index.
 */
char GetChar()
{
	if (index<content.length())
	return (content.charAt(index++));	
	else
	{
		System.out.println("INDEX OUT OF BOUND1");
		return ' ';
	}
}
/**
 * 
 * @param i Number that is added to the index. 
 */
void Swallow(int i)
{
	index=index+i;
}
/**
 * 
 * @return True if the index has reached the end of the file and False if not.
 */
boolean ISDone()
{
	return (index>(content.length()-1));
}
/**
 * 
 * @return The remaining of the string from current index to the end of the file.
 */
String Remainder()
{
	
	return (content.substring(index,content.length()));
}
}
