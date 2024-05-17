public class InstructionCache 
{
	private Word[] cache = new Word[9]; // Cache to hold 8 words plus an additional address word
	private boolean Cache_Valid = false; // Flag to check if cache was written to before
	private Word cachedAddress = new Word(); // Address represented by the first word in the cache
	int clockCycles = 10;
	private L2Cache L2;
	boolean shoulduse;
	InstructionCache(L2Cache L2,boolean shoulduse) 
	{
		cachedAddress.set(-1);
		this.L2 = L2;
		this.shoulduse= shoulduse;
	}

	/**
	 * Reads the instruction at the given address from cache or main memory, handling cache hits and misses.
	 *
	 * @param address The address to read from
	 * @return The word at the address
	 */
	public Word read(Word address) {
		clockCycles = 10;
		if (!Cache_Valid || ((cachedAddress.getUnsigned())+8)<=(address.getUnsigned())) {
			// Cache miss
			clockCycles = 50;
			fillCacheFromL2(address);
		}
		else if((cachedAddress.getUnsigned())>(address.getUnsigned()))//This is if L2 has the cache but L1 is not having the earlier cache
		{
			// Cache miss
			clockCycles = 50;
			fillCacheFromL2(address);
		}


		// Cache hit
		return getWordFromCache(address);
	}

	/**
	 * Fills the cache with 8 words starting from the given address.
	 *
	 * @param address The address to fill the cache from
	 */
	private void fillCache(Word address) {
		Word startAddress = new Word();
		startAddress.copy(address);
		for (int i = 0; i < 8; i++) {
			if (i == 0) {
				cachedAddress.copy(startAddress);
			}
			clockCycles=350;
			cache[i] = MainMemory.read(startAddress);
			startAddress.increment(); // Move to the next address
		}

		// cache[8].set((int) (address.getUnsigned()+8)); // Store the address represented by the first word
		Cache_Valid = true;
	}

	/**
	 * Fills the InstructionCache from the L2Cache for the cost of 50 cycles.
	 * @param address The address to fill the cache from
	 */
	private void fillCacheFromL2(Word address) {
		Word l2CacheWord = L2.read(address);
		int groupIndex = (int) ((address.getUnsigned()+1) / 8);
		l2CacheWord.set(groupIndex);
		if (shoulduse) {
			// Fill the InstructionCache from L2
			Word startAddress = new Word();
			int j= (int) l2CacheWord.getUnsigned()*8;
			startAddress.set(j);

			for (int i = 0; i < 8; i++) {
				if (i == 0) {
					cachedAddress.copy(startAddress);
				}

				cache[i] = L2.cache[(int) startAddress.getUnsigned()%32]; // Read from L2Cache
				startAddress.increment(); // Move to the next address
			}
			cache[8]= new Word();
			cache[8].set((int) (address.getUnsigned()+8)); // Store the address represented by the first word
			Cache_Valid = true;
			clockCycles = 50; // Cost of filling from L2Cache
		} else {
			// If address not found in L2Cache, fill from MainMemory
			fillCache(address);
		}
	}

	/**
	 * Retrieves the word from the cache corresponding to the given address.
	 *
	 * @param address The address to retrieve the instruction for
	 * @return The instruction from the cache
	 */
	private Word getWordFromCache(Word address) {
		// Calculate the offset within the cache
		int offset = (int)(address.getUnsigned())%8;
		//int val= (int)(address.getUnsigned())/8;
		// Return the instruction from the cache
		return cache[offset];
	}


}
