// Class Page is defined separately.
// It stores and returns the Page name via getName()
Page[] pageBuff = new Page[MAX_PAGE_SIZE];
 
public synchronized boolean sendPage(Socket socket, String pageName)
                                     throws IOException {
  // Get the output stream to write the Page to
  ObjectOutputStream out
      = new ObjectOutputStream(socket.getOutputStream());
 
  // Find the Page requested by the client
  // (this operation requires synchronization)
  Page targetPage = null;
  for (Page p : pageBuff) {
    if (p.getName().compareTo(pageName) == 0) {
      targetPage = p;
    }
  }
 
  // Requested Page does not exist
  if (targetPage == null) {
    return false;
  }
 
  // Send the Page to the client
  // (does not require any synchronization)
  out.writeObject(targetPage);
 
  out.flush();
  out.close();
  return true;
}