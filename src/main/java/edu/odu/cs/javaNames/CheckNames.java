package edu.odu.cs.javaNames;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CheckNames {
    private Path dir;

    public CheckNames(String dirPath) {
        dir = Paths.get(dirPath);
    }

    public static void main (String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println(
                "Please supply a path to a directory of Java files\n"
                + "as a command-line parameter");
            System.exit(1);
        }
        Set<String> dubious = new CheckNames(args[0]).checkNames();
        System.out.println ("Found " + dubious.size() + " questionable names:");
        for (String name: dubious) {
            System.out.println("  " + name);
        }
    }

    public Set<String> checkNames() throws IOException {
        Dictionary dictionary = new Dictionary();
        Set<String> badNames = new TreeSet<String>();
        CollectIdentifiers collector = new CollectIdentifiers();
        collector.collect (dir);
        for (String name: collector) {
           Identifier identifier = new Identifier(name);
           List<String> parts = identifier.dissect();
           for (String part: parts) {
              if (part.length() > 1 && !dictionary.contains(part)) {
                badNames.add(name);
                break;
              }
           }
        }
        return badNames;
    }
}
