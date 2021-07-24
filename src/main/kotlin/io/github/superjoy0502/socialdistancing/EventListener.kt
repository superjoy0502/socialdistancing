package io.github.superjoy0502.socialdistancing

import net.kyori.adventure.text.Component.text
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Mob
import org.bukkit.entity.Monster
import org.bukkit.entity.Phantom
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*
import kotlin.random.Random

/**
 * @author superjoy0502
 */

class EventListener : Listener {
    private val pluginManager = Bukkit.getServer().pluginManager
    private val plugin = pluginManager.getPlugin("SocialDistancing") as SocialDistancingPlugin
    private val dataStorer = plugin.dataStorer

    var virusMap: LinkedHashMap<UUID, Boolean> = LinkedHashMap()
    private val server = Bukkit.getServer()
    private val logger = Bukkit.getLogger()

    @EventHandler
    fun onCreatureSpawn(event: CreatureSpawnEvent) {
        if (plugin.socialDistanceLevel >= 1) {
            if (event.entity is Mob) {
                val m: Mob = event.entity as Mob
                if (m is Monster) {
                    if (Random.nextDouble() <= 0.02) {
                        virusMap.put(m.uniqueId, true)
                        dataStorer.storeVirusMap(this.virusMap)
//                        logger.info("DEBUG: Mob with a virus has spawned")
                    }
                }
                if (m is Phantom) {
//                    logger.info("DEBUG: Phantom has Spawned")
                    virusMap.put(m.uniqueId, true)
                    dataStorer.storeVirusMap(this.virusMap)
                }
            }
        }
    }

    @EventHandler
    fun onPlayerDamagedByVirus(event: EntityDamageByEntityEvent) {
        if (event.entity !is Player) return
        val p: Player = event.entity as Player
        if (event.damager !is Mob) return
        val m: Mob = event.damager as Mob
        if (!checkIfMobHasVirus(m.uniqueId)) return
        if (plugin.socialDistanceLevel == 1) {
            p.addPotionEffect(PotionEffect(PotionEffectType.WITHER, 60 * 20, 0))
            plugin.socialDistanceLevel++
            server.broadcast(text(ChatColor.RED.toString() + "바이러스로 인한 피해가 보고되었다!"))
        }
    }

    @EventHandler
    fun onDrinkMilk(event: PlayerItemConsumeEvent) {
        if (event.item.type == Material.MILK_BUCKET) {
            if (event.player.hasPotionEffect(PotionEffectType.WITHER)){
//                logger.info("DEBUG: " + event.player.name + " is drank milk.")
                event.isCancelled = true
            }
        }
    }

    private fun checkIfMobHasVirus(uuid: UUID): Boolean {
        if (uuid in virusMap.keys) {
            if (virusMap.get(uuid) == true) {
                return true
            }
        }
        return false
    }
}