package io.github.monun.sample.plugin

import io.github.monun.kommand.kommand
import net.kyori.adventure.text.Component.text
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Monun
 */
class SamplePlugin : JavaPlugin() {
    override fun onEnable() {
        kommand {
            register("sample") {
                then("ping") {
                    executes {
                        sender.sendMessage(text("pong"))
                    }
                }
            }
        }
    }
}