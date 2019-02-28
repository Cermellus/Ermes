import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProspect } from 'app/shared/model/prospect.model';

type EntityResponseType = HttpResponse<IProspect>;
type EntityArrayResponseType = HttpResponse<IProspect[]>;

@Injectable({ providedIn: 'root' })
export class ProspectService {
    public resourceUrl = SERVER_API_URL + 'api/prospects';

    constructor(protected http: HttpClient) {}

    create(prospect: IProspect): Observable<EntityResponseType> {
        return this.http.post<IProspect>(this.resourceUrl, prospect, { observe: 'response' });
    }

    update(prospect: IProspect): Observable<EntityResponseType> {
        return this.http.put<IProspect>(this.resourceUrl, prospect, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProspect>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProspect[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
