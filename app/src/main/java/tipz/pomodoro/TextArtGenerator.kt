// Copyright (c) 2025 Tipz Team
// SPDX-License-Identifier: Apache-2.0

package tipz.pomodoro

object TextArtGenerator {
    private val zero = """
        | #####  
        |##   ## 
        |##  ### 
        |## # ## 
        |###  ## 
        |##   ## 
        | #####  
    """.trimMargin().split("\n")

    private val one = """
        |   ##   
        |  ###   
        |   ##   
        |   ##   
        |   ##   
        |   ##   
        | ###### 
    """.trimMargin().split("\n")

    private val two = """
        |  ####  
        | ##  ## 
        |     ## 
        |    ##  
        |   ##   
        |  ##    
        | ###### 
    """.trimMargin().split("\n")

    private val three = """
        | ###### 
        |    ##  
        |   ##   
        |    ##  
        |     ## 
        | ##  ## 
        |  ####  
    """.trimMargin().split("\n")

    private val four = """
        |    ##  
        |   ###  
        |  ####  
        | ## ##  
        | ###### 
        |    ##  
        |   #### 
    """.trimMargin().split("\n")

    private val five = """
        | ###### 
        | ##  ## 
        | ##     
        | #####  
        |     ## 
        | ##  ## 
        |  ####  
    """.trimMargin().split("\n")

    private val six = """
        |  ####  
        | ##  ## 
        | ##     
        | #####  
        | ##  ## 
        | ##  ## 
        |  ####  
    """.trimMargin().split("\n")

    private val seven = """
        | ###### 
        | ##  ## 
        |    ##  
        |   ##   
        |  ##    
        |  ##    
        |  ##    
    """.trimMargin().split("\n")

    private val eight = """
        |  ####  
        | ##  ## 
        | ##  ## 
        |  ####  
        | ##  ## 
        | ##  ## 
        |  ####  
    """.trimMargin().split("\n")

    private val nine = """
        |  ####  
        | ##  ## 
        | ##  ## 
        |  ##### 
        |     ## 
        |    ##  
        |  ###   
    """.trimMargin().split("\n")

    private val colon = """
        |        
        |  ##    
        |  ##    
        |        
        |  ##    
        |  ##    
        |        
    """.trimMargin().split("\n")

    fun fromString(s: String): String {
        val fontArray = arrayListOf<List<String>>()

        // Create an array of fonts from string
        for (i in s.indices) {
            val c: Char = s[i]
            val font = when (c) {
                '0' -> zero
                '1' -> one
                '2' -> two
                '3' -> three
                '4' -> four
                '5' -> five
                '6' -> six
                '7' -> seven
                '8' -> eight
                '9' -> nine
                ':' -> colon
                else -> null
            }
            if (font == null) continue
            fontArray.add(font)
        }

        var result = ""
        for (i in 0..6) {
            fontArray.forEach {
                result += it[i]
            }
            result += "\n"
        }
        return result
    }
}