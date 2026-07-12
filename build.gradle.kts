plugins {
    idea
    java
    id("gg.essential.loom") version "1.15.+"
}

val modGroup: String by project
val modID: String by project
val modName: String by project
val modVersion: String by project
val mcVersion: String by project

group = modGroup
version = modVersion

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}

loom {
    runConfigs {
        remove(getByName("server"))
    }

    forge {
        pack200Provider.set(dev.architectury.pack200.java.Pack200Adapter())
        accessTransformer(rootProject.file("src/main/resources/${modID}_at.cfg"))
    }

    // For some reason loom defaults to tab indentation
    decompilers {
        named("vineflower") {
            options.put("indent-string", "    ")
        }
    }
}

repositories {
    mavenCentral()
    maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
    maven("https://nexus.gtnewhorizons.com/repository/public/")
}

dependencies {
    minecraft("com.mojang:minecraft:1.8.9")
    mappings("de.oceanlabs.mcp:mcp_stable:22-1.8.9")
    forge("net.minecraftforge:forge:1.8.9-11.15.1.2318-1.8.9")

    annotationProcessor("com.github.GTNewHorizons:jabel-javac-plugin:1.0.2-GTNH")
        ?.because("Enables use of Java 17 syntax while running on Java 8")
    compileOnly("com.github.GTNewHorizons:jabel-javac-plugin:1.0.2-GTNH")

    runtimeOnly("me.djtheredstoner:DevAuth-forge-legacy:1.2.1")
        ?.because("Allows authenticating into a Minecraft account in dev")
}

sourceSets.main {
    output.setResourcesDir(sourceSets.main.flatMap { it.java.classesDirectory })
}

tasks.withType<JavaCompile>().configureEach {
    targetCompatibility = JavaVersion.VERSION_17.majorVersion
    sourceCompatibility = JavaVersion.VERSION_17.majorVersion
    options.release = 8

    javaCompiler = javaToolchains.compilerFor {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }

    processResources {
        inputs.property("modID", modID)
        inputs.property("modName", modName)
        inputs.property("version", version)
        inputs.property("mcVersion", mcVersion)

        filesMatching(listOf("mcmod.info")) {
            expand(inputs.properties) {
                escapeBackslash = true
            }
        }

        rename("(.+_at.cfg)", "META-INF/$1")
    }

    jar {
        manifest.attributes(mapOf(
            "FMLCorePluginContainsFMLMod" to true,
            "ForceLoadAsMod" to true,
            "FMLAT" to "${modID}_at.cfg",
        ))
    }
}
