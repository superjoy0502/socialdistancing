package io.github.superjoy0502.socialdistancing

import io.github.monun.kommand.kommand
import net.kyori.adventure.text.Component.text
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author superjoy0502
 */

class SocialDistancingPlugin : JavaPlugin() {

    override fun onEnable() {
        setupCommands()
        logger.info(ChatColor.GREEN.toString()  + "플러그인 활성화")
    }

    override fun onDisable() {
        logger.info(ChatColor.RED.toString() + "플러그인 비활성화")
    }

    private fun setupCommands() {
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