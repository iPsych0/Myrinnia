package dev.ipsych0.myrinnia.items;

class DuplicateIDException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -878690703437482903L;

    public DuplicateIDException(String name, int id) throws DuplicateIDException {
        super("Item: " + name + " with ID: " + id + " already exists. Please pick a unique ID!");
        throw this;
    }
}
