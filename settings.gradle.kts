pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://repo.essential.gg/repository/maven-public")
        maven("https://maven.architectury.dev")
        maven("https://maven.fabricmc.net")
        maven("https://maven.minecraftforge.net")
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "gg.essential.loom" -> useModule("gg.essential:architectury-loom:${requested.version}")
            }
        }
    }
}

rootProject.name = "FPK Mod"

sourceControl {
    gitRepository(java.net.URI("https://github.com/Oondanomala/ImGui-LWJGL2.git")) {
        producesModule("loutre.imgui:ImGui-LWJGL2")
    }
}
