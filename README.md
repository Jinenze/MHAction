这是一个简单的动作mod

动画api使用的是[KosmX](https://github.com/KosmX)的[PlayerAnimator](https://github.com/KosmX/minecraftPlayerAnimator)

参考mod [BetterCombat](https://github.com/ZsoltMolnarrr/BetterCombat) [ParCool](https://github.com/alRex-U/ParCool)

以及
[Minecraft实体研究与应用](https://github.com/lovexyn0827/Discovering-Minecraft/tree/master/Minecraft%E5%AE%9E%E4%BD%93%E8%BF%90%E5%8A%A8%E7%A0%94%E7%A9%B6%E4%B8%8E%E5%BA%94%E7%94%A8)
~~我翻了好久才发现玩家移动是client处理的~~

---
以下是一些计划

闪避

~~client触发action~~
~~client发包到server~~
~~server生成实体等待伤害~~

~~server伤害检测~~

~~实体生成 client请求服务器生成实体~~

~~实体存在上限200tick~~

action3阶段结束时清理实体?

攻击

根据actionHeadYaw由client发出射线检测实体

气人值[^1]

[^1]: 故意的

server确认攻击后发包到client client计算值