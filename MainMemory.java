
public class MainMemory 
{
	static Word[] words= new Word[1024];//a static array of 1024 words
	/**
	 * We read the word at the address and return it
	 * @param address
	 * @return Word at the address
	 */
	public static Word read(Word address)
	{
		if(words[(int)address.getUnsigned()]==null)
		{
			words[(int)address.getUnsigned()]=new Word();
		}
		return words[(int)address.getUnsigned()];
	}
	/**
	 * We write the value at the given address
	 * @param address
	 * @param value
	 */
	public static void write(Word address, Word value)
	{
		 if (words[(int)address.getUnsigned()] == null)
		 {
			 words[(int)address.getUnsigned()] = new Word();
	     }
		 words[(int)address.getUnsigned()].copy(value);
	}
	/**
	 * Load will process an array of Strings into your simulated DRAM array, starting with 0. 
	 * @param data the array of string to be inserted into the DRAM
	 */
	public static void load(String[] data)
	{
		for (int i = 0; i < data.length; i++) 
		{
            if (words[i] == null) 
            {
            	words[i] = new Word();
            }
            for(int j =0 ; j<data[i].length();j++)
            {
            words[i].setBit(j, new Bit (data[i].charAt(j)=='1'?true:false));
            }
        }
	}
 
}
