import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICmRequest } from 'app/shared/model/cm-request.model';

type EntityResponseType = HttpResponse<ICmRequest>;
type EntityArrayResponseType = HttpResponse<ICmRequest[]>;

@Injectable({ providedIn: 'root' })
export class CmRequestService {
    public resourceUrl = SERVER_API_URL + 'api/cm-requests';

    constructor(private http: HttpClient) {}

    create(cmRequest: ICmRequest): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cmRequest);
        return this.http
            .post<ICmRequest>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(cmRequest: ICmRequest): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cmRequest);
        return this.http
            .put<ICmRequest>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICmRequest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICmRequest[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(cmRequest: ICmRequest): ICmRequest {
        const copy: ICmRequest = Object.assign({}, cmRequest, {
            instanceHostname:
                cmRequest.instanceHostname != null && cmRequest.instanceHostname.isValid() ? cmRequest.instanceHostname.toJSON() : null,
            startDateTime: cmRequest.startDateTime != null && cmRequest.startDateTime.isValid() ? cmRequest.startDateTime.toJSON() : null,
            endDateTime: cmRequest.endDateTime != null && cmRequest.endDateTime.isValid() ? cmRequest.endDateTime.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.instanceHostname = res.body.instanceHostname != null ? moment(res.body.instanceHostname) : null;
            res.body.startDateTime = res.body.startDateTime != null ? moment(res.body.startDateTime) : null;
            res.body.endDateTime = res.body.endDateTime != null ? moment(res.body.endDateTime) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((cmRequest: ICmRequest) => {
                cmRequest.instanceHostname = cmRequest.instanceHostname != null ? moment(cmRequest.instanceHostname) : null;
                cmRequest.startDateTime = cmRequest.startDateTime != null ? moment(cmRequest.startDateTime) : null;
                cmRequest.endDateTime = cmRequest.endDateTime != null ? moment(cmRequest.endDateTime) : null;
            });
        }
        return res;
    }
}
