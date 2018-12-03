import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICmMock } from 'app/shared/model/cm-mock.model';
import { CmMockService } from './cm-mock.service';

@Component({
    selector: 'jhi-cm-mock-delete-dialog',
    templateUrl: './cm-mock-delete-dialog.component.html'
})
export class CmMockDeleteDialogComponent {
    cmMock: ICmMock;

    constructor(private cmMockService: CmMockService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cmMockService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'cmMockListModification',
                content: 'Deleted an cmMock'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cm-mock-delete-popup',
    template: ''
})
export class CmMockDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cmMock }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CmMockDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.cmMock = cmMock;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
