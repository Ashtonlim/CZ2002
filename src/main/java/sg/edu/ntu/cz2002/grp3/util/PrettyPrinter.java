//Source: https://stackoverflow.com/a/11384393/11224251

package sg.edu.ntu.cz2002.grp3.util;

import java.io.PrintStream;

import static java.lang.String.format;

/**
 * The Class for printing neat rows and columns.
 */
public final class PrettyPrinter {

    /** The Constant BORDER_KNOT. */
    private static final char BORDER_KNOT = '+';
    
    /** The Constant HORIZONTAL_BORDER. */
    private static final char HORIZONTAL_BORDER = '-';
    
    /** The Constant VERTICAL_BORDER. */
    private static final char VERTICAL_BORDER = '|';

    /** The Constant DEFAULT_AS_NULL. */
    private static final String DEFAULT_AS_NULL = "(NULL)";

    /** The out. */
    private final PrintStream out;
    
    /** The as null. */
    private final String asNull;

    public PrettyPrinter(PrintStream out) {
        this(out, DEFAULT_AS_NULL);
    }

    /**
     * Instantiates a new pretty printer.
     *
     * @param out the out
     * @param asNull the as null
     */
    public PrettyPrinter(PrintStream out, String asNull) {
        if ( out == null ) {
            throw new IllegalArgumentException("No print stream provided");
        }
        if ( asNull == null ) {
            throw new IllegalArgumentException("No NULL-value placeholder provided");
        }
        this.out = out;
        this.asNull = asNull;
    }

    /**
     * Prints the table.
     *
     * @param table the table
     */
    public void print(String[][] table) {
        if ( table == null ) {
            throw new IllegalArgumentException("No tabular data provided");
        }
        if ( table.length == 0 ) {
            return;
        }
        final int[] widths = new int[getMaxColumns(table)];
        adjustColumnWidths(table, widths);
        printPreparedTable(table, widths, getHorizontalBorder(widths));
    }

    /**
     * Prints the prepared table.
     *
     * @param table the table
     * @param widths the widths
     * @param horizontalBorder the horizontal border
     */
    private void printPreparedTable(String[][] table, int widths[], String horizontalBorder) {
        final int lineLength = horizontalBorder.length();
        out.println(horizontalBorder);
        for ( final String[] row : table ) {
            if ( row != null ) {
                out.println(getRow(row, widths, lineLength));
                out.println(horizontalBorder);
            }
        }
    }

    /**
     * Gets the row.
     *
     * @param row the row
     * @param widths the widths
     * @param lineLength the line length
     * @return the row
     */
    private String getRow(String[] row, int[] widths, int lineLength) {
        final StringBuilder builder = new StringBuilder(lineLength).append(VERTICAL_BORDER);
        final int maxWidths = widths.length;
        for ( int i = 0; i < maxWidths; i++ ) {
            builder.append(padRight(getCellValue(safeGet(row, i, null)), widths[i])).append(VERTICAL_BORDER);
        }
        return builder.toString();
    }

    /**
     * Gets the horizontal border.
     *
     * @param widths the widths
     * @return the horizontal border
     */
    private String getHorizontalBorder(int[] widths) {
        final StringBuilder builder = new StringBuilder(256);
        builder.append(BORDER_KNOT);
        for ( final int w : widths ) {
            for ( int i = 0; i < w; i++ ) {
                builder.append(HORIZONTAL_BORDER);
            }
            builder.append(BORDER_KNOT);
        }
        return builder.toString();
    }

    /**
     * Gets the max columns.
     *
     * @param rows the rows
     * @return the max columns
     */
    private int getMaxColumns(String[][] rows) {
        int max = 0;
        for ( final String[] row : rows ) {
            if ( row != null && row.length > max ) {
                max = row.length;
            }
        }
        return max;
    }

    /**
     * Adjust column widths.
     *
     * @param rows the rows
     * @param widths the widths
     */
    private void adjustColumnWidths(String[][] rows, int[] widths) {
        for ( final String[] row : rows ) {
            if ( row != null ) {
                for ( int c = 0; c < widths.length; c++ ) {
                    final String cv = getCellValue(safeGet(row, c, asNull));
                    final int l = cv.length();
                    if ( widths[c] < l ) {
                        widths[c] = l;
                    }
                }
            }
        }
    }

    /**
     * Pad right.
     *
     * @param s the s
     * @param n the n
     * @return the string
     */
    private static String padRight(String s, int n) {
        return format("%1$-" + n + "s", s);
    }

    /**
     * Safe get.
     *
     * @param array the array
     * @param index the index
     * @param defaultValue the default value
     * @return the string
     */
    private static String safeGet(String[] array, int index, String defaultValue) {
        return index < array.length ? array[index] : defaultValue;
    }

    /**
     * Gets the cell value.
     *
     * @param value the value
     * @return the cell value
     */
    private String getCellValue(Object value) {
        return value == null ? asNull : value.toString();
    }

}