package fun.gusmurphy.chesses.piece;

import static fun.gusmurphy.chesses.engine.PlayerColor.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import fun.gusmurphy.chesses.engine.PlayerColor;
import fun.gusmurphy.chesses.engine.piece.Piece;
import fun.gusmurphy.chesses.engine.piece.PieceType;

public class PieceSprite {

    public static Sprite spriteFor(Piece piece) {
        return new Sprite(new Texture(fileNameFor(piece)));
    }

    private static String fileNameFor(Piece piece) {
        return fileNameFor(piece.color(), piece.type());
    }

    private static String fileNameFor(PlayerColor color, PieceType pieceType) {
        String fileName = color == WHITE ? "w_" : "b_";

        switch (pieceType) {
            case KING:
                fileName += "king";
                break;
            case QUEEN:
                fileName += "queen";
                break;
            case ROOK:
                fileName += "rook";
                break;
            case BISHOP:
                fileName += "bishop";
                break;
            case KNIGHT:
                fileName += "knight";
                break;
            case PAWN:
                fileName += "pawn";
                break;
        }

        fileName += ".png";

        return fileName;
    }
}
