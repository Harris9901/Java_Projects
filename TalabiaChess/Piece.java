import java.awt.*;
//Liew
//MV-MODEL CLASS
public abstract class Piece {
    private int row, col;
    private String color;
    private Board board;
    private Image image;

    public Piece(int row, int col, String color, Board board, Image image) {
        this.row = row;
        this.col = col;
        this.color = color;
        this.board = board;
        this.image = image;
    }

    // Setter method to update the row coordinate of the piece
    public void setRow(int row) {
        this.row = row;
    }

    // Setter method to update the column coordinate of the piece
    public void setCol(int col) {
        this.col = col;
    }

    // Getter method to retrieve the row coordinate of the piece
    public int getRow() {
        return row;
    }

    // Getter method to retrieve the column coordinate of the piece
    public int getCol() {
        return col;
    }

    // Getter method to retrieve the color of the piece
    public String getColor() {
        return color;
    }

    // Getter method to retrieve the board on which the piece is placed
    public Board getBoard() {
        return board;
    }

    // Setter method to update the image representing the piece
    public void setImage(Image image) {
        this.image = image;
    }

    // Getter method to retrieve the image representing the piece
    public Image getImage() {
        return image;
    }

    // Abstract method to be implemented by concrete subclasses to determine if the
    // piece can move to the specified coordinates
    public abstract boolean move(int row, int col);
}