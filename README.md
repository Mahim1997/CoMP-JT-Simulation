# Coordinated Multi-Point (CoMP) Networks Simulation
A simulation tool for simulating 5G-CoMP networks under various conditions including Conventional, Dynamic Point Selection (DPS) and Joint Transmission (JT)

# Codes for CoMP networks in-depth simulation. 
 
* Java used for simulation codes (and preliminary graph generation)
* MATLAB used for (final) graph generation

## Documentation:
Please see [CoMP_JT.pdf](https://github.com/Mahim1997/CoMP-JT-Simulation/blob/master/CoMP_JT.pdf) for more details.

## Metrics simulated include the following :

### Average metrics against traffic rate

- Avg throughput considering **all** UEs (using SINR, Shanon's formula)
- Avg throughput considering only **active** UEs (using SINR, Shanon's formula)
- Cell-Edge Throughput
- Proportion of active UEs
- Proportion of dropped UEs
- Effective traffic
- Entropy
- Discrimination Index
- Jain's Fairness Index
- Spectral Efficiency
- Avg Throughput

### Additional metrics

- Average throughput vs Number of Co-ordinating Base-Stations (Conventional, DPS, JT=2,3,4,5)
- Variation by including/not including the concept of a **dummy ring**.
