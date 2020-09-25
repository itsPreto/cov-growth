package com.jmc.covgrowth.api

import java.util.HashMap

class GivelifyChallenge {

    // caching HashMap
    lateinit var lengths: HashMap<Long, Long>

    fun main(args: Array<String>) {
        val end = 1000000L
        var longestIndex: Long = 1

        //avoid calculating numbers we've checked (caching mechanism I proposed)
        lengths = HashMap()
        lengths[1L] = 1L

        //iterate through till our threshold
        for (i in 2 until end) {
            val length = this.getLength(i)
            //DebugUtil.defaultOutput.println(" [" + length + "] ");
            if (length >= lengths[longestIndex]!!) {
                longestIndex = i
            }
        }
        println(longestIndex.toString() + " with length " + lengths[longestIndex])
    }

    // recursive function for calculating chains and checking if a cached result already exists.
    fun getLength(i: Long): Long {
        //if chain length previously calculated, return immediately
        return if (this.lengths.containsKey(i)) {
            this.lengths[i]!!
        } else {
            if (i % 2 == 0L) {
                //DebugUtil.defaultOutput.print(null, i + " -> ");
                this.lengths[i] = 1 + getLength(i / 2)
                this.lengths[i]!!
            } else {
                this.lengths[i] = 1 + getLength(3 * i + 1)
                this.lengths[i]!!
            }
        }
    }
}