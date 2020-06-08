import java.util.ArrayList;


/**
 * Created by christopherlau on 6/16/17.
 */
public class Node {

    /** A node indexed at COL and ROW. */
    Node(int id, int colNum, int rowNum, int num, Board board) {
        _id = id;
        _colNum = colNum;
        _rowNum = rowNum;
        _num = num;
        if (num != 0) {
            _confirmed = true;
        } else {
            _confirmed = false;
        }
        _board = board;
        _possible = new ArrayList<>();
    }

    void initializeRow(Block row){
        _row = row;
    }

    void initializeCol(Block col){
        _col = col;
    }

    void initializeBlk(Block blk){
        _blk = blk;
    }

    /** Return private id of node. */
    int id() {
        return _id;
    }

    /** Return column number of node. */
    int colNum() {
        return _colNum;
    }

    /** Return row number of node. */
    int rowNum() {
        return _rowNum;
    }

    /** Return number of node. */
    int num() {
        return _num;
    }

    /** Return TRUE if confirmed number. */
    boolean confirmed() {
        return _confirmed;
    }

    /** Return COL block of node. */
    Block col() {
        return _col;
    }

    /** Return ROW block of node. */
    Block row() {
        return _row;
    }

    /** Return block of node. */
    Block blk() {
        return _blk;
    }

    /** Change node to confirmed. */
    public void confirm() {
        _confirmed = true;
    }

    /** Set node to confirmed number. */
    public void setNum(int num) {
        this._num = num;
        confirm();
        this._possible = new ArrayList<>();
    }

    /** Identifying number of node. */
    private int _id;

    /** Column number of node. */
    private int _colNum;

    /** Row number of node. */
    private int _rowNum;

    /** Number contained within the node. */
    private int _num;

    /** TRUE if confirmed node. */
    private boolean _confirmed;

    /** Col block. */
    private Block _col;

    /** Row block. */
    private Block _row;

    /** Blk block. */
    private Block _blk;

    /** Board. */
    public Board _board;

    /** Strategy function: find all numbers it can be. */
    void strategy() {
        _possible = new ArrayList<>();
        if (this.confirmed()) {
            return;
        }
        for (int num = 1; num < 10; num++) {
            if (!this.col().hasNum(num) & !this.row().hasNum(num) & !this.blk().hasNum(num)) {
                _possible.add(num);
            }
        }
        strategy1();
    }

    /** Only 1 possible number. */
    void strategy1() {
        if (_possible.size() == 1) {
            setNum(_possible.get(0));
        }
    }

    /** Check for conflicts with node. */
    boolean hasConflict() {
        if (this.confirmed()) {
            if (this.col().duplicate(this.num()) || this.row().duplicate(this.num()) || this.blk().duplicate(this.num())) {
                return true;
            }
        } else {
            if (this._possible.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /** Return array of all possible numbers. */
    ArrayList<Integer> possible() {
        return _possible;
    }

    /** Array to list all numbers it can be. */
    private ArrayList<Integer> _possible;

}
