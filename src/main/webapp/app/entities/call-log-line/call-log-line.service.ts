import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICallLogLine } from 'app/shared/model/call-log-line.model';

type EntityResponseType = HttpResponse<ICallLogLine>;
type EntityArrayResponseType = HttpResponse<ICallLogLine[]>;

@Injectable({ providedIn: 'root' })
export class CallLogLineService {
    public resourceUrl = SERVER_API_URL + 'api/call-log-lines';

    constructor(protected http: HttpClient) {}

    create(callLogLine: ICallLogLine): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(callLogLine);
        return this.http
            .post<ICallLogLine>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(callLogLine: ICallLogLine): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(callLogLine);
        return this.http
            .put<ICallLogLine>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICallLogLine>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICallLogLine[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(callLogLine: ICallLogLine): ICallLogLine {
        const copy: ICallLogLine = Object.assign({}, callLogLine, {
            callLogDueDate:
                callLogLine.callLogDueDate != null && callLogLine.callLogDueDate.isValid()
                    ? callLogLine.callLogDueDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.callLogDueDate = res.body.callLogDueDate != null ? moment(res.body.callLogDueDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((callLogLine: ICallLogLine) => {
                callLogLine.callLogDueDate = callLogLine.callLogDueDate != null ? moment(callLogLine.callLogDueDate) : null;
            });
        }
        return res;
    }
}
