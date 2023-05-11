package net.thumbtack.school.auction.exception;
public class ServerException extends Exception {

    private ServerErrorCode serverErrorCode;

    public ServerErrorCode getUserErrorCode() {
        return serverErrorCode;
    }
    public ServerException(ServerErrorCode serverErrorCode) {
        this.serverErrorCode = serverErrorCode;
    }
}
