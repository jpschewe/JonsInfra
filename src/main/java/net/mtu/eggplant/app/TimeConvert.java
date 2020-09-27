package net.mtu.eggplant.app;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JTextField;

import net.mtu.eggplant.util.gui.GraphicsUtils;

/**
 * Nifty little class that converts a date as a long to a human readable string.
 */
public class TimeConvert extends JPanel {

  /**
   * @param args ignored
   **/
  public static void main(final String[] args) {
    final TimeConvert tc = new TimeConvert();
    GraphicsUtils.basicGUIMain(tc, false, "Time Convert");
  }

  /**
   * Constructor.
   */
  public TimeConvert() {
    super(new BorderLayout());

    final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS zzz");
    final JTextField time = new JTextField("MM/dd/yyyy HH:mm:ss.SSS zzz");

    final JTextField number = new JTextField();
    number.addActionListener(new NumberListener(time, number, format));

    time.addActionListener(new TimeListener(time, number, format));

    add(number, BorderLayout.NORTH);
    add(time, BorderLayout.CENTER);
  }

  private static class BaseListener {
    @SuppressWarnings("checkstyle:visibilitymodifier")
    protected final JTextField mTime;

    @SuppressWarnings("checkstyle:visibilitymodifier")
    protected final JTextField mNumber;

    @SuppressWarnings("checkstyle:visibilitymodifier")
    protected final DateFormat mFormat;

    BaseListener(final JTextField time,
                 final JTextField number,
                 final DateFormat format) {
      mTime = time;
      mNumber = number;
      mFormat = format;
    }
  }

  private static class NumberListener extends BaseListener implements ActionListener {
    NumberListener(final JTextField time,
                   final JTextField number,
                   final DateFormat format) {
      super(time, number, format);
    }

    @Override
    public void actionPerformed(final ActionEvent ae) {
      final Date d = new Date(Long.parseLong(mNumber.getText()));
      mTime.setText(mFormat.format(d));
    }
  }

  private static class TimeListener extends BaseListener implements ActionListener {
    TimeListener(final JTextField time,
                 final JTextField number,
                 final DateFormat format) {
      super(time, number, format);
    }

    @Override
    public void actionPerformed(final ActionEvent ae) {
      try {
        mNumber.setText(String.valueOf(mFormat.parse(mTime.getText()).getTime()));
      } catch (final ParseException pe) {
        mNumber.setText("-1");
      }
    }
  }

}
