import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICallLog } from 'app/shared/model/call-log.model';

type EntityResponseType = HttpResponse<ICallLog>;
type EntityArrayResponseType = HttpResponse<ICallLog[]>;

@Injectable({ providedIn: 'root' })
export class CallLogService {
    public resourceUrl = SERVER_API_URL + 'api/call-logs';

    constructor(protected http: HttpClient) {}

    create(callLog: ICallLog): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(callLog);
        return this.http
            .post<ICallLog>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(callLog: ICallLog): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(callLog);
        return this.http
            .put<ICallLog>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICallLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICallLog[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(callLog: ICallLog): ICallLog {
        const copy: ICallLog = Object.assign({}, callLog, {
            callLogDate: callLog.callLogDate != null && callLog.callLogDate.isValid() ? callLog.callLogDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.callLogDate = res.body.callLogDate != null ? moment(res.body.callLogDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((callLog: ICallLog) => {
                callLog.callLogDate = callLog.callLogDate != null ? moment(callLog.callLogDate) : null;
            });
        }
        return res;
    }
}
