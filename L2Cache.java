public class L2Cache
{
	public Word[] cache = new Word[32]; // Cache to hold 32 words
	private boolean[] Cache_Valid = new boolean[4]; // Flags to check if each group of 8 words in the cache is valid
	private Word[] cachedAddresses = new Word[4]; // Addresses represented by the first word in each group
	int clockCycles = 0;
	boolean calledmem= false;

	L2Cache() 
	{
		for (int i = 0; i < 4; i++) 
		{
			cachedAddresses[i] = new Word();
			cachedAddresses[i].set(-1); // Initialize cached addresses to -1
		}
	}

	/**
	 * Reads the word at the given address from the L2 cache.
	 *
	 * @param address The address to read from
	 * @return The index
	 */
	public Word read(Word address) {
		calledmem= false;
		// Calculate the group index within the cache
		int groupIndex = (int) ((address.getUnsigned()) / 8);
		if(groupIndex>=4)
		{
			groupIndex= groupIndex%4;
		}
		if (!Cache_Valid[groupIndex] || ((cachedAddresses[groupIndex].getUnsigned())+8)<=(address.getUnsigned())) {
			// Cache miss
			calledmem= true;

			clockCycles = 350;
			fillCache(address, groupIndex);
			Word result= new Word();
			result.set(groupIndex);
			return result;
		}

		// Cache hit
		return getWordFromCache(address, groupIndex);
	}

	/**
	 * Fills the group of 8 words in the L2 cache starting from the given address.
	 *
	 * @param address     The address to fill the cache from
	 * @param groupIndex The index of the group within the cache
	 */
	private void fillCache(Word address, int groupIndex) {
		Word startAddress = new Word();
		startAddress.copy(address);

		for (int i = 0; i < 8; i++) {
			if (i == 0) {
				cachedAddresses[groupIndex].copy(startAddress);
			}

			cache[groupIndex * 8 + i] = MainMemory.read(startAddress);
			startAddress.increment(); // Move to the next address
		}

		Cache_Valid[groupIndex] = true;
	}

	/**
	 * Retrieves the word from the L2 cache corresponding to the given address.
	 *
	 * @param address     The address to retrieve the word for
	 * @param groupIndex The index of the group within the cache
	 * @return The group index for the cache to store the stuff it needs from the L2 
	 */
	private Word getWordFromCache(Word address, int groupIndex) {
		// Calculate the offset within the cache
		int offset = (int) (address.getUnsigned() - cachedAddresses[groupIndex].getUnsigned());
		Word result= (new Word());
		result.set(groupIndex);
		// Return the group index from the L2 cache
		return result;
	}
}
