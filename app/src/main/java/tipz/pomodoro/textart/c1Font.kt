// Copyright (c) 2025 Tipz Team
// SPDX-License-Identifier: Apache-2.0

package tipz.pomodoro.textart

import tipz.pomodoro.R

class c1Font : TextArtFont {
    override val zero = """
        | #####  
        |##   ## 
        |##  ### 
        |## # ## 
        |###  ## 
        |##   ## 
        | #####  
    """.trimMargin().split("\n")

    override val one = """
        |   ##   
        |  ###   
        |   ##   
        |   ##   
        |   ##   
        |   ##   
        | ###### 
    """.trimMargin().split("\n")

    override val two = """
        |  ####  
        | ##  ## 
        |     ## 
        |    ##  
        |   ##   
        |  ##    
        | ###### 
    """.trimMargin().split("\n")

    override val three = """
        | ###### 
        |    ##  
        |   ##   
        |    ##  
        |     ## 
        | ##  ## 
        |  ####  
    """.trimMargin().split("\n")

    override val four = """
        |    ##  
        |   ###  
        |  ####  
        | ## ##  
        | ###### 
        |    ##  
        |   #### 
    """.trimMargin().split("\n")

    override val five = """
        | ###### 
        | ##  ## 
        | ##     
        | #####  
        |     ## 
        | ##  ## 
        |  ####  
    """.trimMargin().split("\n")

    override val six = """
        |  ####  
        | ##  ## 
        | ##     
        | #####  
        | ##  ## 
        | ##  ## 
        |  ####  
    """.trimMargin().split("\n")

    override val seven = """
        | ###### 
        | ##  ## 
        |    ##  
        |   ##   
        |  ##    
        |  ##    
        |  ##    
    """.trimMargin().split("\n")

    override val eight = """
        |  ####  
        | ##  ## 
        | ##  ## 
        |  ####  
        | ##  ## 
        | ##  ## 
        |  ####  
    """.trimMargin().split("\n")

    override val nine = """
        |  ####  
        | ##  ## 
        | ##  ## 
        |  ##### 
        |     ## 
        |    ##  
        |  ###   
    """.trimMargin().split("\n")

    override val colon = """
        |        
        |  ##    
        |  ##    
        |        
        |  ##    
        |  ##    
        |        
    """.trimMargin().split("\n")

    override val fontHeight = 7
    override val fontTextSize = R.dimen.timer_mode_c1_size
}