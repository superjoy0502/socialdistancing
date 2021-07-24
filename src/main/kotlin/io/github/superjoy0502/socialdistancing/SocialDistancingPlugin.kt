package io.github.superjoy0502.socialdistancing

import io.github.monun.kommand.kommand
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.annotations.NotNull


/**
 * @author superjoy0502
 */

class SocialDistancingPlugin : JavaPlugin() {
    private val eventListener: EventListener = EventListener()
    private val dataStorer: DataStorer = DataStorer()
    private val plugin: JavaPlugin = this

    var socialDistanceLevelText: String = "평상시 (1)"
    var socialDistanceLevel: Int = 0
        set(value) {
            when (value) {
                0 -> {
                    socialDistanceLevelText = "평상시 (1)"
                }
                1 -> {
                    socialDistanceLevelText = "평상시 (2)"
                }
                2 -> {
                    if (value > socialDistanceLevel) {
                        server.broadcast(text(ChatColor.RED.toString() + "바이러스로 인한 피해가 보고되었다!"))
                    } else if (value < socialDistanceLevel) {
                        server.broadcast(text(ChatColor.GREEN.toString() + "피해가 조금은 나아진 것 같다!"))
                    }
                    socialDistanceLevelText = "사회적 거리두기 1"
                }
                3 -> {
                    if (value > socialDistanceLevel) {
                        server.broadcast(text(ChatColor.RED.toString() + "바이러스가 조금 위험한 것 같다!"))
                    } else if (value < socialDistanceLevel) {
                        server.broadcast(text(ChatColor.GREEN.toString() + "피해가 조금은 나아진 것 같다!"))
                    }
                    socialDistanceLevelText = "사회적 거리두기 2"
                }
                4 -> {
                    if (value > socialDistanceLevel) {
                        server.broadcast(text(ChatColor.RED.toString() + "바이러스를 걷잡을 수 없다!"))
                    }
                    socialDistanceLevelText = "사회적 거리두기 3"
                }
            }
            field = value
        }
    /*
        0: 평상시 (1)
        1: 평상시 (2)
        2: 사회적 거리두기 1단계
        3: 사회적 거리두기 2단계
        4: 사회적 거리두기 3단계
    */

    override fun onEnable() {
        setupCommands()

        server.pluginManager.registerEvents(eventListener, this)
        eventListener.virusMap = dataStorer.virusMap

        logger.info(ChatColor.GREEN.toString() + "플러그인 활성화")
    }

    override fun onDisable() {
        dataStorer.storeVirusMap(eventListener.virusMap)

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
            register("socialdistancing") {
                then("start"){
                    executes {
                        world.time = 0
                        for (player in Bukkit.getOnlinePlayers()) {
                            player.showTitle(Title.title(text(ChatColor.GREEN.toString() + "사회적 거리두기 야생"), text(ChatColor.GREEN.toString() + "Day 0")))
                            player.playSound(Sound.UI_TOAST_CHALLENGE_COMPLETE as @NotNull net.kyori.adventure.sound.Sound)
                        }
                        val scheduler = server.scheduler
                        scheduler.scheduleSyncDelayedTask(plugin, Runnable {
                            @Override
                            fun run(){
                                socialDistanceLevel = 1
                            }
                        }, 24000L)
                    }
                }
                then("increase") {
                    executes {
                        if (socialDistanceLevel < 4) {
                            socialDistanceLevel++
                            server.broadcast(text(ChatColor.RED.toString() + "관리자가 임의로 사회적 거리두기 단계를 " + socialDistanceLevel + "로 격상했습니다."))
                        }
                    }
                }
                then("decrease") {
                    executes {
                        if (socialDistanceLevel > 0){
                            socialDistanceLevel--
                            server.broadcast(text(ChatColor.GREEN.toString() + "관리자가 임의로 사회적 거리두기 단계를 " + socialDistanceLevel + "로 완화했습니다."))
                        }
                    }
                }
            }
        }
    }
}