# scala

## Type Inference
- Compiler can infer the type of a variable looking at RHS
  - e.g. val val1 = "Kamran" // Compiler knows type of val1 is String
- Compiler can infer the return type of functions by looking at implementation
  - e.g. def func(x: Int) = x + 1 // Compiler notices the return type of ` x + 1 ` is Int, figuring out the return type of function as INT
  - Caveat: For recursive functions, compiler can't figure out the return type 