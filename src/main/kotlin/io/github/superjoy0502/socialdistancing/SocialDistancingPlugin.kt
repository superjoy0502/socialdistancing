package io.github.superjoy0502.socialdistancing

import io.github.monun.kommand.kommand
import net.kyori.adventure.text.Component.text
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author superjoy0502
 */

class SocialDistancingPlugin : JavaPlugin() {
    var socialDistanceLevel: Int = 0

    override fun onEnable() {
        setupCommands()

        server.pluginManager.registerEvents(SocialDistancingEventListener(), this)

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
            register("sd"){
                then("increase"){
                    executes {
                        socialDistanceLevel++
                        server.broadcast(text(ChatColor.RED.toString() + "관리자가 임의로 사회적 거리두기 단계를 " + socialDistanceLevel + "로 격상했습니다."))
                    }
                }
                then("decrease"){
                    executes {
                        socialDistanceLevel--
                        server.broadcast(text(ChatColor.GREEN.toString() + "관리자가 임의로 사회적 거리두기 단계를 " + socialDistanceLevel + "로 완화했습니다."))
                    }
                }
            }
        }
    }
}