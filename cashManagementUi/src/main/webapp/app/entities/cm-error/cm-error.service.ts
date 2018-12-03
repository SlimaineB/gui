import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICmError } from 'app/shared/model/cm-error.model';

type EntityResponseType = HttpResponse<ICmError>;
type EntityArrayResponseType = HttpResponse<ICmError[]>;

@Injectable({ providedIn: 'root' })
export class CmErrorService {
    public resourceUrl = SERVER_API_URL + 'api/cm-errors';

    constructor(private http: HttpClient) {}

    create(cmError: ICmError): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cmError);
        return this.http
            .post<ICmError>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(cmError: ICmError): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cmError);
        return this.http
            .put<ICmError>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICmError>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICmError[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(cmError: ICmError): ICmError {
        const copy: ICmError = Object.assign({}, cmError, {
            errornDateTime: cmError.errornDateTime != null && cmError.errornDateTime.isValid() ? cmError.errornDateTime.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.errornDateTime = res.body.errornDateTime != null ? moment(res.body.errornDateTime) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((cmError: ICmError) => {
                cmError.errornDateTime = cmError.errornDateTime != null ? moment(cmError.errornDateTime) : null;
            });
        }
        return res;
    }
}
