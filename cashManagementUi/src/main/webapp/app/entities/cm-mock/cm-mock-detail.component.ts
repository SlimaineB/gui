import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICmMock } from 'app/shared/model/cm-mock.model';

@Component({
    selector: 'jhi-cm-mock-detail',
    templateUrl: './cm-mock-detail.component.html'
})
export class CmMockDetailComponent implements OnInit {
    cmMock: ICmMock;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cmMock }) => {
            this.cmMock = cmMock;
        });
    }

    previousState() {
        window.history.back();
    }
}
