
public class Word 
{
	Bit array[] = new Bit [32];//array of 32 instances of the Bit class
	/**
	 * The constructor of the word class
	 */
	public Word() 
	{
		for (int i = 0; i < 32; i++) 
		{
			array[i] = new Bit(false);
        }
	}
	/**
	 * Gets a new Bit that has the same value as bit i
	 * @param i the index.
	 * @return A bit at
	 */
	Bit getBit(int i)
	{
	   return new Bit(array[i].getValue());
	}
	/**
	 * set bit i's value
	 * @param i The index to which we want to set the new bit
	 * @param value The bit value we want to set at the index i
	 */
	void setBit(int i,Bit value)
	{
		array[i].set(value.getValue());
	}
	/**
	 * And operation on two words, returning a new Word
	 * @param other The second word which does an and with the currant word
	 * @return A new word after the and operation
	 */
	Word and(Word other)
	{
		Word newword= new Word();
		for(int i=0;i<32;i++)
		{
		newword.setBit(i, array[i].and(other.getBit(i)));
		}
		return newword;
	}
	/**
	 * or operation on two words, returning a new Word
	 * @param other The second word which does an or operation with the currant word
	 * @return A new word after the or operation
	 */
    Word or(Word other) 
	{
        Word newword = new Word();
        for (int i = 0; i < 32; i++) 
        {
        	newword.setBit(i, array[i].or(other.getBit(i)));
        }
        return newword;
    }
    /**
     * Xor operation on two words, returning a new Word
	 * @param other The second word which does a Xor operation with the currant word
	 * @return A new word after the Xor operation
     */
    Word Xor(Word other) 
	{
        Word newword = new Word();
        for (int i = 0; i < 32; i++) 
        {
        	newword.setBit(i, array[i].Xor(other.getBit(i)));
        }
        return newword;
    }
    /**
     *  negate this word, creating a new Word
     * @return A new word after negation
     */
	 Word not() 
	 {
	        Word newword = new Word();
	        for (int i = 0; i < 32; i++) 
	        {
	        	newword.setBit(i, array[i].not());
	        }
	        return newword;
	 }
	 /**
	  * right shift this word by amount bits, creating a new Word
	  * @param amount The numbers we want to shift to the right 
	  * @return The new word after right shift
	  */
	 Word rightShift(int amount) 
	 {
	        Word newword = new Word();
	        for (int i = 31; i >=amount; i--) 
	        {
	        	newword.setBit(i , this.getBit(i - amount));
	        }
	        return newword;
	 }
	 /**
	  * Left shift this word by amount bits, creating a new Word
	  * @param amount The numbers we want to shift to the left 
	  * @return The new word after left shift
	  */
	 Word leftShift(int amount) 
	 {
	        Word newword = new Word();
	        for (int i = 31; i >= amount; i--) 
	        {
	        	newword.setBit(i - amount, this.getBit(i));
	        }
	        return newword;
	  }
	/**
	 * returns a comma separated string t’s and f’s
	 */
	  public String toString()
	 {
		 String output="";
		 for (int i = 0; i < 32; i++) 
	        {
			 if(i<31)
	        {
			 if(array[i].getValue())
			       output= output+"t,";
			 else
				   output= output+"f,";
	        }
			 else
			 {
				 if(array[i].getValue())
				       output= output+"t";
				 else
					   output= output+"f";
			 }
	        }
			return output;
	 }
	 /**
	  * returns the value of this word as a long
	  * @return the value of this word as a long
	  */
	 public long getUnsigned() 
	 {
		    long output = 0;
		    for (int i = 0; i < 32; i++) 
		    {
		        if (array[i].getValue()) 
		        {
		            long p = 1;
		            for (int j = 0; j < 31 - i; j++)
		            {
		                p = p * 2;
		            }
		            output = output + p;
		        }
		    }
		    return output;
		}
     /**
      * 
      * @return returns the value of this word as an int
      */ 
	 public int getSigned() 
	 {
		 Bit copy_array[]= new Bit[32];
		 for(int i=0;i<32;i++)
		 {
			 copy_array[i]= new Bit(false);
		 }
		 for(int i=0;i<32;i++)
		 {
			 copy_array[i].set(array[i].getValue());
		 }
		    int output = 0;
		    for (int i = 0; i < 32; i++) 
		    {
		        if (copy_array[i].getValue()) 
		        {
		            int p = 1;
		            for (int j = 0; j < 31 - i; j++)
		            {
		                p = p * 2;
		            }
		            output = output + p;
		        }
		    }
		    if (copy_array[0].getValue())
		    {
		        for (int i = 0; i < 32; i++) 
		        {
		        	copy_array[i].set(!copy_array[i].getValue());
		        }
		        int stop = 1;
		        for (int i = 31; i >= 0 && stop > 0; i--) {
		            if (copy_array[i].getValue()) 
		            {
		            	copy_array[i].set(false);
		            } 
		            else 
		            {
		            	copy_array[i].set(true);
		                stop = 0;
		            }
		        }
		        output = 0;
		        for (int i = 0; i < 32; i++) 
		        {
		            if (copy_array[i].getValue())
		            {
		                int p = 1;
		                for (int j = 0; j < 31 - i; j++)
		                {
		                    p = p * 2;
		                }
		                output = output + p;
		            }
		        }
		        output = -output;
		    }
		    return output;
		}
	 /**
	  * copies the values of the bits from another Word into this one
	  * @param other The another Word we need to copy
	  */
	 void copy(Word other)
	 {
		 for (int i=0; i<32;i++)
		 {
			 array[i].set(other.getBit(i).getValue());
		 }
	 }
	 /**
	  * set the value of the bits of this Word (used for tests)
	  * @param value The int value converted to bit 
	  */
	 public void set(int value) 
	 {   
		    if (value < 0)
		    {
		        value = -value; // Make the value positive for now
		        for (int i = 31; i >= 0; i--) 
			    {
			        array[i].set(value % 2 == 1);
			        value = value / 2;
			    }

		        // Negating all the values on the word
		        for (int i = 0; i < 32; i++)
		        {
		            array[i].toggle();
		        }
		        // adding 1 to get the 2's complement
		        int stop = 1;
		        for (int i = 31; i >= 0 && stop > 0; i--) 
		        {
		            if (array[i].getValue())
		            {
		                array[i].set(false);
		            } 
		            else
		            {
		                array[i].set(true);
		                stop = 0;
		            }
		        }
		    }
		    else 
		    {
		    // Set the bits for the absolute value of the input
		    for (int i = 31; i >= 0; i--) 
		    {
		        array[i].set(value % 2 == 1);
		        value = value / 2;
		    }
		
		}
	 }
	public Bit[] getBits() {
		// TODO Auto-generated method stub
		return array;
	}
	/**
	 * The increment methode to add 1 to the word
	 */
	public void increment() 
	{
		 Bit bit_1 = new Bit(true);//The one bit that we want to add
		 int i = 31;
		 while (i >=0 && bit_1.getValue())//We loop till we get a false as a carry 
		 {
		        Bit bit2 = array[i];
		        array[i] = bit2.Xor(bit_1);
		        bit_1 = bit2.and(bit_1);
		        i--;
		  }    
	}
}
