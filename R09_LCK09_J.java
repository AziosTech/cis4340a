// No synchronization
public boolean sendPage(Socket socket, String pageName) {
    Page targetPage = getPage(pageName);
   
    if (targetPage == null){
      return false;
    }
    return deliverPage(socket, targetPage);
  }
   
  // Requires synchronization
  private synchronized Page getPage(String pageName) {
    Page targetPage = null;
   
    for (Page p : pageBuff) {
      if (p.getName().equals(pageName)) {
        targetPage = p;
      }
    }
    return targetPage;
  }
   
  // Return false if an error occurs, true if successful
  public boolean deliverPage(Socket socket, Page page) {
    ObjectOutputStream out = null;
    boolean result = true;
    try {
      // Get the output stream to write the Page to
      out = new ObjectOutputStream(socket.getOutputStream());
   
      // Send the page to the client
      out.writeObject(page);out.flush();
    } catch (IOException io) {
      result = false;
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
          result = false;
        }
      }
    }
    return result;
  }