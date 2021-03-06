package ar.com.eduit.curso.java.util.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class FileText implements I_File{
    private File file; //file representa un objeto archivo.

    public FileText(File file)   { this.file = file;             } //constructor
    public FileText(String file) { this.file = new File(file);   }
    
    
    @Override
    public void print() {
        int car;
        try (FileReader in = new FileReader(file)){ //in provee un stream de bytes 
            while((car = in.read()) != -1) System.out.print((char)car); //in.read me devuelve el primer caracter, a la vez q el apuntador apunta a la sig letra
        } catch (Exception e) { e.printStackTrace(); }
        System.out.println();
    }

    @Override
    public String getText() {
        //String text = ""; 
        StringBuilder sb = new StringBuilder();
        int car;
        try (FileReader in = new FileReader(file)){
            while((car = in.read()) != -1) sb.append((char) car);
        } catch (Exception e) { e.printStackTrace(); }
        //return text;
        return sb.toString();
    }

    @Override public void setText(String text) {
         try (FileWriter out = new FileWriter(file)){
             out.write(text);
        } catch (Exception e) { e.printStackTrace(); }
        
    }

    @Override public void appendText(String text) { //metodo que agrega al final
        try (FileWriter out = new FileWriter(file, true)){ //el booleano como true nos permite agregarlo atrás
             out.write(text);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override public void clear() { setText("");   }
    @Override public void addLine(String line) { appendText(line + "\n"); }
    @Override public void addLines(Collection<String> lines) {
        lines.forEach(this :: addLine);
    }

    @Override
    public List<String> getLines() {
        List<String> lista = new ArrayList();
        try (BufferedReader in = new BufferedReader(new FileReader(file))){
            lista = in.lines().collect(Collectors.toList()); //me devuelve el stream. Leo todas las lineas y lo convierto en un list.
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }


    @Override
    public List<String> getLines(String filter) {
        return getLines()                                                  //obtiene la lista total
                .stream()                                                  //se transforma en un stram filtrable
                .filter(p->p.toLowerCase().contains(filter.toLowerCase())) //filtramos
                .collect(Collectors.toList());                             //convertimos en List
                }

    @Override public List<String> getSortedLines() {
        return getLines()
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override public List<String> getReversedLines() {
        return getSortedLines()
                .stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    @Override public LinkedHashSet<String> getLinkedHashSetLines() {
        LinkedHashSet<String> set = new LinkedHashSet();
        set.addAll(getLines());
        return set;
    }

    @Override public TreeSet<String> getTreeSetLines() { 
        TreeSet<String> set = new TreeSet();
        set.addAll(getLines());
        return set;
    }

    @Override
    public void removeLine(String line) {
        List<String> lista = getLines();
        lista.remove(line);
        clear();
        addLines(lista);
    }
    
    
    
}
