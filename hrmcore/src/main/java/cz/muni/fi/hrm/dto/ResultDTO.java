package cz.muni.fi.hrm.dto;

public class ResultDTO {
    public Double matchInPerc;
    public Integer pointsFitInMargin;
    public Integer numberOfPoints;
    public String refCurveName;
    public RefCurveDTO averageCurve;

    public ResultDTO() {
        this(null, null, null, null, null);
    }

    public ResultDTO(Double matchInPerc, Integer pointsFitInMargin, Integer numberOfPoints,
                     String refCurveName, RefCurveDTO averageCurve) {
        this.matchInPerc = matchInPerc;
        this.pointsFitInMargin = pointsFitInMargin;
        this.numberOfPoints = numberOfPoints;
        this.refCurveName = refCurveName;
        this.averageCurve = averageCurve;
    }

    public Double getMatchInPerc() {
        return matchInPerc;
    }

    public void setMatchInPerc(Double matchInPerc) {
        this.matchInPerc = matchInPerc;
    }

    public void setPointsFitInMargin(Integer pointsFitInMargin) {
        this.pointsFitInMargin = pointsFitInMargin;
    }

    public Integer getPointsFitInMargin() {
        return pointsFitInMargin;
    }

    public void setNumberOfPoints(Integer numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }

    public Integer getNumberOfPoints() {
        return numberOfPoints;
    }

    public String getRefCurveName() {
        return refCurveName;
    }

    public void setRefCurveName(String refCurveName) {
        this.refCurveName = refCurveName;
    }

    public RefCurveDTO getAverageCurve() {
        return averageCurve;
    }

    public void setAverageCurve(RefCurveDTO averageCurve) {
        this.averageCurve = averageCurve;
    }


}
