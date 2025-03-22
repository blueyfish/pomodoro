package tipz.pomodoro.textart

import tipz.pomodoro.R

class AnsiShadowFont : TextArtFont {
    override val zero = """
        | ██████╗ 
        |██╔═████╗
        |██║██╔██║
        |████╔╝██║
        |╚██████╔╝
        | ╚═════╝ 
    """.trimMargin().split("\n")

    override val one = """
        |   ██╗   
        |  ████║  
        |   ╚██║  
        |    ██║  
        |    ██║  
        |    ╚═╝  
    """.trimMargin().split("\n")

    override val two = """
        | ██████╗ 
        | ╚════██╗
        |  █████╔╝
        | ██╔═══╝ 
        | ███████╗
        | ╚══════╝
    """.trimMargin().split("\n")

    override val three = """
        | ██████╗ 
        | ╚════██╗
        |  █████╔╝
        |  ╚═══██╗
        | ██████╔╝
        | ╚═════╝ 
    """.trimMargin().split("\n")

    override val four = """
        | ██╗  ██╗
        | ██║  ██║
        | ███████║
        | ╚════██║
        |      ██║
        |      ╚═╝
    """.trimMargin().split("\n")

    override val five = """
        | ███████╗
        | ██╔════╝
        | ███████╗
        | ╚════██║
        | ███████║
        | ╚══════╝
    """.trimMargin().split("\n")

    override val six = """
        | ██████╗ 
        |██╔════╝ 
        |███████╗ 
        |██╔═══██╗
        |╚██████╔╝
        | ╚═════╝ 
    """.trimMargin().split("\n")

    override val seven = """
        | ███████╗
        | ╚════██║
        |     ██╔╝
        |    ██╔╝ 
        |    ██║  
        |    ╚═╝  
    """.trimMargin().split("\n")

    override val eight = """
        |  █████╗ 
        | ██╔══██╗
        | ╚█████╔╝
        | ██╔══██╗
        | ╚█████╔╝
        |  ╚════╝ 
    """.trimMargin().split("\n")

    override val nine = """
        |  █████╗ 
        | ██╔══██╗
        | ╚██████║
        |  ╚═══██║
        |  █████╔╝
        |  ╚════╝ 
    """.trimMargin().split("\n")

    override val colon = """
        |         
        |   ██╗   
        |   ╚═╝   
        |   ██╗   
        |   ╚═╝   
        |         
    """.trimMargin().split("\n")

    override val fontHeight = 6
    override val fontTextSize = R.dimen.timer_mode_ansi_shadow_size
}