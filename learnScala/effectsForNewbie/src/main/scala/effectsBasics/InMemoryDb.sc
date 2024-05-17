/*
Strategy:
  - Keep code pure
  - run effects at boundary of program


  Intended effects:
    - effect of function and expression to produce value
  Side effects:
    - A change in state variable
    - a message print on console
    - change in db
    - exception being thrown


A funtion is pure
   - it has no side effect
   - same argument should produce same value

Side effectful expresion can be purified by monads
  - Mutable state  -> State
  - Exception      -> Option, Either
  - Writing to console/ disk/ network -> IO

 */