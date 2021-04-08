package net.mtu.eggplant.xml;

import static org.checkerframework.checker.nullness.util.NullnessUtil.castNonNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * An {@link Iterable} for {@link Element}s in a {@link NodeList}.
 */
public final class NodelistElementCollectionAdapter implements Iterator<Element>, Iterable<Element> {

  @Override
  public Iterator<Element> iterator() {
    return this;
  }

  /**
   * Walks the iterator and converts to a {@link LinkedList}.
   *
   * @return a list from the iterator
   */
  public List<Element> asList() {
    final List<Element> retval = new LinkedList<>();
    for (final Element ele : this) {
      retval.add(ele);
    }
    return retval;
  }

  private final NodeList nodelist;

  /**
   * Index of next element to return.
   */
  private int nextIndex;

  /**
   * @param nodelist the node list to iterate over
   */
  public NodelistElementCollectionAdapter(final NodeList nodelist) {
    this.nodelist = nodelist;
    // initialize to -1 so that findNext starts looking at 0
    this.nextIndex = -1;
    findNextElement();
  }

  @Override
  public boolean hasNext() {
    return nextIndex < nodelist.getLength();
  }

  @Override
  public Element next() {
    if (hasNext()) {
      final Element retval = (Element) nodelist.item(nextIndex);
      findNextElement();

      // hasNext guarantees that nodelist.item(nextIndex) will not return null
      return castNonNull(retval);
    } else {
      throw new NoSuchElementException();
    }
  }

  /**
   * @throws UnsupportedOperationException this method is not supported
   */
  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

  /**
   * Starting at nextIndex+1, find the next {@link Element} in the
   * {@link NodeList}.
   */
  private void findNextElement() {
    do {
      ++nextIndex;
    } while (nextIndex < nodelist.getLength()
        && !(nodelist.item(nextIndex) instanceof Element));
  }
}