import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
public class TokenManager 
{
	private LinkedList<Token> Token_Stream = new LinkedList<>();//Linked list of the token Stream
	public TokenManager(List<Token> tokens) // Constructor to initialize the 'tokens' list
	{
		this.Token_Stream = new LinkedList<>(tokens);
	}
/**
 * Peek method to return what is at the j position in the linked list
 * @param j index of the token that can be present in the linked list
 * @return Optional<Token> 
 */
	Optional<Token> Peek(int j) 
	{
		if(MoreTokens())
		{
		if (Token_Stream.size()<=j||j<0)//if the size of the token list is less then j or j is less then 0 then the peek is unsuccessful and out of bound and empty Optional is returned
		{
			return Optional.empty();		
		}
		else
		{
			return Optional.of((Token_Stream.get(j)));
		}
		}
		return Optional.empty();
	}
	/**
	 * MoreTokens method to check if we have more tokens in the manager or not
	 * @return true if there are more tokens else false
	 */
	Boolean MoreTokens()
	{
		if (Token_Stream.isEmpty())	//if the linked list of tokens is empty then false
			return false;
		else
			return true;
	}
	/**
	 * Matches the first token in the token stream with the specified TokenType 't', 
	 * removes it from the stream if it matches, and returns an Optional containing 
	 * the matched token. If no match is found, an empty Optional is returned.
	 *
	 * @param t The TokenType to match with the first token in the stream.
	 * @return An Optional containing the matched token if found, or an empty Optional if not.
	 */
	Optional<Token> MatchAndRemove(Token.TokenType t) 
	{
	   if(MoreTokens())
	   {
		 if(Token_Stream.getFirst().getType()==t)
		 {
			  return Optional.of(Token_Stream.poll());//if matched then will remove the given token from the list.
		 }
		 else
		 {
	            return Optional.empty(); // Return an empty Optional if the list is empty.
	     }
	   }
	   else
           return Optional.empty(); // Return an empty Optional if the list is empty.
	}
	public Iterator<Token> iterator() {
        return Token_Stream.iterator();
    }
}
  