# 404
# SIA32 Processor Simulation

## Introduction

This project is a Java-based simulation of the SIA32 processor architecture. The SIA32 architecture is designed with four instruction formats and supports a variety of operations, including arithmetic, logic, branching, memory access, and control operations.

## Instruction Formats

The SIA32 processor uses four primary instruction formats, each identified by a unique binary prefix:

1. **3 Register (3R)**: `11`
   - Format: Immediate (8 bits) | Rs1 (5 bits) | Rs2 (5 bits) | Function (4 bits) | Rd (5 bits) | Opcode (5 bits)
   - Example: Arithmetic operations involving two source registers and one destination register.

2. **2 Register (2R)**: `10`
   - Format: Immediate (13 bits) | Rs (5 bits) | Function (4 bits) | Rd (5 bits) | Opcode (5 bits)
   - Example: Arithmetic operations involving one source register and one destination register.

3. **Destination Only (1R)**: `01`
   - Format: Immediate (18 bits) | Function (4 bits) | Rd (5 bits) | Opcode (5 bits)
   - Example: Operations with a single destination register and an immediate value.

4. **No Register (0R)**: `00`
   - Format: Immediate (27 bits) | Opcode (5 bits)
   - Example: Operations that do not require any registers, such as HALT.

## Registers

- **General Purpose Registers**: 32 registers (R0-R31). R0 is hardcoded to 0 (NO OP).
- **Special Purpose Registers**: Stack Pointer (SP) and Program Counter (PC). These are modified by specific instructions like branch, call, return, push, and pop.

## Operation Codes (Opcodes)

The opcode is a combination of the instruction code and the instruction format. For example, a 3R math operation has an opcode `00011`.

## Supported Operations

### Mathematical Operations (MOP)

- `1000`: AND
- `1001`: OR
- `1010`: XOR
- `1011`: NOT (negate op1; ignore op2)
- `1100`: Left shift (op1 is the value to shift, op2 is the amount to shift; ignore all but the lowest 5 bits)
- `1101`: Right shift (op1 is the value to shift, op2 is the amount to shift; ignore all but the lowest 5 bits)
- `1110`: Add
- `1111`: Subtract
- `0111`: Multiply

### Boolean Operations (BOP)

- `0000`: Equals (EQ)
- `0001`: Not Equal (NEQ)
- `0010`: Less Than (LT)
- `0011`: Greater than or Equal (GE)
- `0100`: Greater Than (GT)
- `0101`: Less than or Equal (LE)

## Project Structure

- `ALU.java`: Implements arithmetic logic unit operations.
- `Assembler.java`: Assembles instructions into machine code.
- `Bit.java`: Helper class for bitwise operations.
- `InstructionCache.java`: Manages instruction cache.
- `L2Cache.java`: Manages level 2 cache.
- `MainMemory.java`: Simulates the main memory.
- `Processor.java`: Coordinates the functioning of the processor.
- `READ_handler.java`: Handles read operations.
- `Token.java`: Represents tokens in the assembly process.
- `TokenManager.java`: Manages tokens.
- `Word.java`: Represents a word in memory.
- `test.java`: Contains test cases.
- `test_Assembler.java`: Tests the assembler functionality.
- `test_Cache.java`: Tests the cache functionality.

## Getting Started

1. **Clone the repository**:
   ```sh
   git clone <repository-url>
   cd <repository-directory>
   ```

2. **Compile the Java files**:
   ```sh
   javac *.java
   ```

3. **Run the processor simulation**:
   ```sh
   java Processor
   ```

## Running Tests

To run the test cases, compile and execute the test files:
```sh
javac test_*.java
java test
```

## Contributing

Contributions are welcome! Please submit pull requests with clear descriptions of the changes made.

## License

This project is licensed under the MIT License.

For more information, refer to the `LICENSE` file.

---

This README provides an overview of the SIA32 processor simulation project, explaining the instruction formats, operations, and how to set up and run the simulation.
