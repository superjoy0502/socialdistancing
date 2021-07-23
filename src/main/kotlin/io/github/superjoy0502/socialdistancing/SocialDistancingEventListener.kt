package io.github.superjoy0502.socialdistancing

import org.bukkit.plugin.PluginManager
import org.bukkit.Bukkit
import io.github.superjoy0502.socialdistancing.SocialDistancingPlugin
import net.kyori.adventure.text.Component.text
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Mob
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.entity.Monster
import org.bukkit.entity.Phantom
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*
import kotlin.collections.LinkedHashMap
import kotlin.random.Random

class SocialDistancingEventListener : Listener {
    val pluginManager = Bukkit.getServer().pluginManager
    val plugin = pluginManager.getPlugin("SocialDistancing") as SocialDistancingPlugin

    var virusMap: LinkedHashMap<UUID, Boolean> = LinkedHashMap()

    @EventHandler
    fun onCreatureSpawn(event: CreatureSpawnEvent) {
        if (plugin.socialDistanceLevel >= 1) {
            if (event.entity is Mob) {
                val m: Mob = event.entity as Mob
                if (m is Monster){
                    if (Random.nextFloat() <= 0.02){
                        virusMap.put(m.uniqueId, true)
                        Bukkit.getLogger().info("DEBUG: Mob with a virus has spawned")
                    }
                }
                if (m is Phantom){
                    Bukkit.getLogger().info("DEBUG: Phantom has Spawned")
                    virusMap.put(m.uniqueId, true)
                }
            }
        }
    }

    @EventHandler
    fun onPlayerDamagedByVirus(event: EntityDamageByEntityEvent){
        if (event.entity !is Player) return
        val p: Player = event.entity as Player
        if (event.damager !is Mob) return
        val m: Mob = event.damager as Mob
        if (!checkIfMobHasVirus(m.uniqueId)) return
        if (plugin.socialDistanceLevel == 1){
            p.addPotionEffect(PotionEffect(PotionEffectType.WITHER, 60*20, 0))
            plugin.socialDistanceLevel++
            Bukkit.getServer().broadcast(text(ChatColor.RED.toString() + "바이러스로 인한 피해가 보고되었다!"))
        }
    }

    @EventHandler
    fun onDrinkMilk(event: PlayerItemConsumeEvent){
        if (event.item.type == Material.MILK_BUCKET){
            event.isCancelled = true
        }
    }

    private fun checkIfMobHasVirus(uuid: UUID): Boolean{
        if (uuid in virusMap.keys){
            if (virusMap.get(uuid) == true){
                return true
            }
        }
        return false
    }
}