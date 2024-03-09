
public class Bit 
{
private Boolean bit;//boolean bit value
/**
 * The bit constructor value of true or false as 1 and 0 respectively
 * @param b  The default value of the bit
 */
public Bit(boolean b) 
{
	bit = b ;
}
/**
 * The set method to set the bit value
 * @param value The given value of the boolean
 */
public void set(Boolean value)
{
	bit= value;
}
/**
 * changes the value from true to false or false to true
 */
void toggle()
{
	bit=!bit;
}
/**
 * sets the bit to true
 */
void set()
{
	bit=true;
}
/**
 * sets the bit to false
 */
void clear()
{
	bit = false;
}
/**
 * returns the value of the bit
 * @return the value of the bit
 */
Boolean getValue()
{
	return bit;
}
/**
 *  performs and on two bits and returns a new bit set to the result
 * @param other the second bit.
 * @return the bit after performing and operation
 */
Bit and(Bit other) 
{
	if(bit == true)
		return other;
	else
		return new Bit(false);
}
/**
 * performs or on two bits and returns a new bit set to the result
 * @param other The second bit
 * @return The bit after performing or operation
 */
Bit or(Bit other)
{
	if(bit == false)
		return other;
	else
		return new Bit(true);  
}
/**
 * Performs Xor on two bits and returns a new bit set to the result 
 * @param other The second bit 
 * @return The bit after performing Xor operation
 */
Bit Xor(Bit other)
{
	if(bit == false)
		return other;
	else
	{
		return other.not(); 
	}
}
/**
 * performs not on the existing bit, returning the result as a new bit
 * @return The new bit after not operation 
 */
Bit not()
{
	Bit output = new Bit(!getValue());
	return output;
}
/**
 * returns “t” or “f”
 */
public String toString()
{
	if(bit)
		return "t";
	else
		return "f";
}
}
