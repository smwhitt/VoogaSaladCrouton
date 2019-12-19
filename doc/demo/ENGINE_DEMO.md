---
marp: true
theme: uncover
_class: invert
---

# **Crouton: Engine**
Micheal Head, Lakshya Bakshi, Alex Qiao, Nishant Iyengar, Milan Shah

---

### Overview of Design
![Overview](Overview.GIF)

---
### Overview of Design (cont.)
Primary Design Methods:
- Composition in Entity-Components
- Component-based Systems
- Use of unique IDs

---
## Goals
1. Flexible to behavior extension
2. Flexible to new types of Entities
3. Limit access to Entities across classes
4. Dynamic changes to Entity behavior

---
## Features
- Components: Health, Speed, Angle, etc.
- Various component systems: Movement, Collision, Pathing, Victory Condition
- Front end APIs: create(), place(), getEntityVisuals()
- XStream: serializing and deserializing 
---
## Tests
JUnit tests

---
## Moving forward
1. Extend behaviors to be more complex (new components and systems)
2. Integrate with Game Player
3. Develop more systems and authorable elements

