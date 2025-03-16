import java.util.*;

class Process {
    int pid, arrivalTime, burstTime;

    Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}

public class EnergyAwareSchedulerr {

    public static void scheduleProcesses(List<Process> processes) {
        int currentTime = 0;
        double totalEnergy = 0.0;

        System.out.println("PID\tCompletion Time\tEnergy (J)");

        // Sort processes by burst time (Shortest Job First - SJF)
        processes.sort(Comparator.comparingInt(p -> p.burstTime));

        for (Process process : processes) {
            // Ensure CPU waits for process arrival
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }

            // Set frequency dynamically based on burst time
            double frequency = determineFrequency(process.burstTime);
            
            // Calculate energy consumption
            double energy = computeEnergy(frequency, process.burstTime);

            // Update time and total energy
            currentTime += process.burstTime;
            totalEnergy += energy;

            System.out.printf("%d\t%d\t%.2f J\n", process.pid, currentTime, energy);
        }

        System.out.printf("\nTotal Energy Consumption: %.2f J\n", totalEnergy);
    }

    private static double determineFrequency(int burstTime) {
        if (burstTime > 10) return 1.0;
        if (burstTime > 5) return 2.0;
        return 3.0;
    }

    private static double computeEnergy(double frequency, int burstTime) {
        return 50 * Math.pow(frequency / 3.0, 2) * burstTime;
    }

    public static void main(String[] args) {
        List<Process> processList = Arrays.asList(
            new Process(1, 0, 4),
            new Process(2, 2, 12),
            new Process(3, 4, 7),
            new Process(4, 6, 3)
        );

        scheduleProcesses(processList);
    }
}
