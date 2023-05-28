# Mandala
**Mandala** is a notebook software.

## Script-writing Style
一個突發奇想的嘗試, 把軟體當成劇本寫作.

其中 Use Cases Layer 的有許多幕(Act), 一幕會上演多場戲(Scene). 戲中的演員(Actors)是 Domain Model(Entity).
- Play Scene 需要給於 Context.
- 仍然是 Actor 製造 Event 

> On Stage, Act 的區分是"**需要把布幕拉起來換背景**"; 一個 Scene 的區分是"**發生一個可以推動故事/劇情(Story)的事件**"


## Run
```bash
mvn spring-boot:run
```